package cn.lefer.tomu.service;

import cn.lefer.tomu.cache.OnlineStatus;
import cn.lefer.tomu.constant.SongSource;
import cn.lefer.tomu.constant.SongStatus;
import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.entity.ChannelSongRel;
import cn.lefer.tomu.entity.PlayHistory;
import cn.lefer.tomu.entity.Song;
import cn.lefer.tomu.event.ChannelEvent;
import cn.lefer.tomu.event.ChannelEventService;
import cn.lefer.tomu.event.ChannelEventType;
import cn.lefer.tomu.event.detail.*;
import cn.lefer.tomu.exception.BizErrorCode;
import cn.lefer.tomu.exception.BizRestException;
import cn.lefer.tomu.mapper.ChannelMapper;
import cn.lefer.tomu.mapper.ChannelSongRelMapper;
import cn.lefer.tomu.mapper.PlayHistoryMapper;
import cn.lefer.tomu.mapper.SongMapper;
import cn.lefer.tomu.queue.MessagePool;
import cn.lefer.tomu.utils.TomuUtils;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.Page;
import cn.lefer.tomu.view.SongView;
import cn.lefer.tools.Date.LeferDate;
import cn.lefer.tools.Net.LeferNet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/16
 * @Description : 频道服务的一个实现
 */
@Service
public class ChannelServiceImpl implements ChannelService {
    private final ChannelMapper channelMapper;
    private final PlayHistoryMapper playHistoryMapper;
    private final SongMapper songMapper;
    private final ChannelEventService channelEventService;
    private final MessagePool messagePool;
    private final List<SongStatus> defaultSongStatusList;
    private final OnlineStatus onlineStatus;
    private final ChannelSongRelMapper channelSongRelMapper;

    @Autowired
    public ChannelServiceImpl(ChannelMapper channelMapper,
                              ChannelSongRelMapper channelSongRelMapper,
                              PlayHistoryMapper playHistoryMapper,
                              SongMapper songMapper,
                              OnlineStatus onlineStatus,
                              ChannelEventService channelEventService,
                              MessagePool messagePool) {
        List<SongStatus> songStatusList = new ArrayList<>();
        songStatusList.add(SongStatus.NORMAL);
        songStatusList.add(SongStatus.OUTDATE);
        this.defaultSongStatusList = songStatusList;
        this.channelMapper = channelMapper;
        this.channelSongRelMapper=channelSongRelMapper;
        this.playHistoryMapper = playHistoryMapper;
        this.songMapper = songMapper;
        this.channelEventService = channelEventService;
        this.messagePool = messagePool;
        this.onlineStatus = onlineStatus;
    }

    @Override
    public ChannelView createChannel() {
        Channel channel = new Channel();
        channel.setChannelCreateDate(LeferDate.today());
        channelMapper.insert(channel);
        return new ChannelView(channel);
    }

    @Override
    public ChannelView getChannel(int channelID, String token) {
        Channel channel = channelMapper.selectByID(channelID);
        if (channel == null) return null;
        PlayHistory playHistory = playHistoryMapper.selectPlayStatusByChannelID(channelID);
        if (playHistory != null) {
            Song song = songMapper.selectByID(playHistory.getSongID());
            channel.setCurrentSong(song);
            channel.setPosition(playHistory.getLastPosition());
        }
        //将用户进入的事件推入广播
        EnterChannelEventDetail detail = new EnterChannelEventDetail();
        detail.setChannelID(channelID);
        detail.setDate(LeferDate.today());
        detail.setNickName(TomuUtils.getNickname(token));
        ChannelEvent.Builder<EnterChannelEventDetail> builder = new ChannelEvent.Builder<>();
        channelEventService.broadcast(channelID, token, builder.withType(ChannelEventType.AUDIENCE_IN).withDetail(detail).build());
        return new ChannelView(channel);
    }

    @Override
    public SongView addSong(int channelID,
                            String token,
                            String songName,
                            String artistName,
                            String coverUrl,
                            String lrcUrl,
                            String mp3Url,
                            double songDuration,
                            SongSource songSource,
                            String songUrl) throws IOException {
        Date now = LeferDate.today();
        //先判断歌真不真
        if(!LeferNet.isValid(mp3Url)) throw new BizRestException(BizErrorCode.URL_TEST_FAILED);
        //再判断歌在不在
        Song song = songMapper.selectBySongNameAndArtistNameOrMP3Url(songName,artistName,mp3Url);
        if(song==null){
            song = new Song();
            song.setSongAddDate(now);
            song.setMp3Url(mp3Url);
            song.setLrcUrl(lrcUrl);
            song.setCoverUrl(coverUrl);
            song.setArtistName(artistName);
            song.setSongDuration(songDuration);
            song.setSongName(songName);
            song.setSongSource(songSource);
            song.setSongUrl(songUrl);
            song.setSongStatus(SongStatus.NORMAL);
            songMapper.insert(song);
        }else{
            //再判断关系在不在
            int count = channelSongRelMapper.existsRelWithChannelIDAndSongID(channelID,song.getSongID());
            //关系不在，建关系,关系在，报重复
            if(count>0){
                throw new BizRestException(BizErrorCode.REPEATED_SONG);
            }else{
                channelSongRelMapper.insert(new ChannelSongRel(channelID,song.getSongID(),now,true));
            }
        }
        SongView songView= new SongView(song);
        //将增加歌曲的事件推入广播
        AddSongEventDetail detail = new AddSongEventDetail();
        detail.setChannelID(channelID);
        detail.setDate(now);
        detail.setSongView(songView);
        ChannelEvent.Builder<AddSongEventDetail> builder = new ChannelEvent.Builder<>();
        channelEventService.broadcast(channelID, token, builder.withType(ChannelEventType.ADD_SONG).withDetail(detail).build());
        return songView;
    }

    @Override
    public boolean deleteSong(int channelID, int songID, String token) {
        channelSongRelMapper.delete(channelID,songID);
        //将删除歌曲的事件推入广播
        DeleteSongEventDetail detail = new DeleteSongEventDetail();
        detail.setSongID(songID);
        detail.setChannelID(channelID);
        detail.setDate(LeferDate.today());
        ChannelEvent.Builder<DeleteSongEventDetail> builder = new ChannelEvent.Builder<>();
        channelEventService.broadcast(channelID, token, builder.withType(ChannelEventType.DELETE_SONG).withDetail(detail).build());
        return true;
    }

    @Override
    public Channel playSong(int channelID, int songID) {
        return null;
    }

    @Override
    public Page<SongView> getSongs(int channelID, int pageNum, int pageSize) {
        List<Song> songs = songMapper.selectByChannelID(channelID, defaultSongStatusList, pageNum, pageSize);
        int total = songMapper.countByChannelID(channelID, defaultSongStatusList);
        List<SongView> songViews = songs.stream().map(SongView::new).collect(Collectors.toList());
        Page.Builder<SongView> pageBuilder = new Page.Builder<>();
        return pageBuilder.pageNum(pageNum).pageSize(pageSize).total(total).data(songViews).build();
    }

    @Override
    public List<SongView> getSongs(int channelID) {
        List<Song> songs = songMapper.selectAllByChannelID(channelID, defaultSongStatusList);
        return songs.stream().map(SongView::new).collect(Collectors.toList());
    }


    @Override
    public boolean hasNewsInChannel(int channelID, String token) {
        return !channelEventService.isEmpty(TomuUtils.getNickname(token));
    }

    @Override
    public ServerSentEvent<ChannelEvent<? extends AbstractChannelEventDetail>> getChannelEvent(int channelID, String token, String seq) {
        ChannelEvent<? extends AbstractChannelEventDetail> channelEvent = channelEventService.get(TomuUtils.getNickname(token));
        return ServerSentEvent.<ChannelEvent<? extends AbstractChannelEventDetail>>builder()
                .event(channelEvent.getType().toString())
                .id(seq)
                .data(channelEvent)
                .build();

    }


    @Override
    public boolean changeChannelStatus(int channelID, int songID, double position, String token) {
        Date now = LeferDate.today();
        //记入事件队列，对外广播
        ChannelEvent.Builder<ChannelPlayStatusChangeEventDetail> builder = new ChannelEvent.Builder<>();
        ChannelPlayStatusChangeEventDetail detail = new ChannelPlayStatusChangeEventDetail();
        detail.setChannelID(channelID);
        detail.setDate(now);
        detail.setPosition(position);
        detail.setSongID(songID);
        channelEventService.broadcast(channelID, token, builder.withType(ChannelEventType.CHANGE_PLAY_STATUS).withDetail(detail).build());
        //记入持久化队列，交由异步线程持久化
        PlayHistory playHistory = new PlayHistory();
        playHistory.setSongID(songID);
        playHistory.setChannelID(channelID);
        playHistory.setLastPosition(position);
        playHistory.setPlayDate(now);
        messagePool.getMessageProducer().onData("insert", PlayHistory.class.getName(), playHistory);
        return true;
    }

    @Override
    public boolean exit(int channelID, String token) {
        //记入事件队列，对外广播
        ExitChannelEventDetail detail = new ExitChannelEventDetail();
        detail.setDate(LeferDate.today());
        detail.setChannelID(channelID);
        detail.setNickName(TomuUtils.getNickname(token));
        ChannelEvent.Builder<ExitChannelEventDetail> builder = new ChannelEvent.Builder<>();
        channelEventService.broadcast(channelID, token, builder.withType(ChannelEventType.AUDIENCE_OUT).withDetail(detail).build());
        onlineStatus.exit(token, channelID);
        return true;
    }

    @Override
    public List<String> getAudienceWithNickName(int channelID) {
        return onlineStatus.getAudienceWithNickName(channelID);
    }
}
