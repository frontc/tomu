package cn.lefer.tomu.service;

import cn.lefer.tomu.cache.ChannelStatus;
import cn.lefer.tomu.cache.OnlineStatus;
import cn.lefer.tomu.constant.SongSource;
import cn.lefer.tomu.constant.SongStatus;
import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.entity.PlayHistory;
import cn.lefer.tomu.entity.Song;
import cn.lefer.tomu.event.ChannelEvent;
import cn.lefer.tomu.event.ChannelEventService;
import cn.lefer.tomu.event.ChannelEventType;
import cn.lefer.tomu.event.detail.AbstractChannelEventDetail;
import cn.lefer.tomu.event.detail.ChannelPlayStatusChangeEventDetail;
import cn.lefer.tomu.mapper.ChannelMapper;
import cn.lefer.tomu.mapper.PlayHistoryMapper;
import cn.lefer.tomu.mapper.SongMapper;
import cn.lefer.tomu.queue.MessagePool;
import cn.lefer.tomu.utils.TomuUtils;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.Page;
import cn.lefer.tomu.view.PlayStatusView;
import cn.lefer.tomu.view.SongView;
import cn.lefer.tools.Date.LeferDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
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
    private final ChannelStatus channelStatus;
    private final ChannelEventService channelEventService;
    private final MessagePool messagePool;
    private final List<SongStatus> defaultSongStatusList;
    private final OnlineStatus onlineStatus;

    @Autowired
    public ChannelServiceImpl(ChannelMapper channelMapper,
                              PlayHistoryMapper playHistoryMapper,
                              SongMapper songMapper,
                              ChannelStatus channelStatus,
                              OnlineStatus onlineStatus,
                              ChannelEventService channelEventService,
                              MessagePool messagePool) {
        List<SongStatus> songStatusList = new ArrayList<>();
        songStatusList.add(SongStatus.NORMAL);
        songStatusList.add(SongStatus.OUTDATE);
        this.defaultSongStatusList = songStatusList;
        this.channelMapper = channelMapper;
        this.playHistoryMapper = playHistoryMapper;
        this.songMapper = songMapper;
        this.channelStatus = channelStatus;
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
    public ChannelView getChannel(int channelID) {
        Channel channel = channelMapper.selectByID(channelID);
        if (channel == null) return null;
        PlayHistory playHistory = playHistoryMapper.selectPlayStatusByChannelID(channelID);
        if (playHistory != null) {
            Song song = songMapper.selectByID(playHistory.getSongID());
            channel.setCurrentSong(song);
            channel.setPosition(playHistory.getLastPosition());
        }
        return new ChannelView(channel);
    }

    @Override
    public SongView addSong(int channelID,
                            String songName,
                            String artistName,
                            String coverUrl,
                            String lrcUrl,
                            String mp3Url,
                            double songDuration,
                            SongSource songSource,
                            String songUrl) {
        Song song = new Song();
        song.setSongAddDate(LeferDate.today());
        song.setMp3Url(mp3Url);
        song.setLrcUrl(lrcUrl);
        song.setCoverUrl(coverUrl);
        song.setArtistName(artistName);
        song.setChannelID(channelID);
        song.setSongDuration(songDuration);
        song.setSongName(songName);
        song.setSongSource(songSource);
        song.setSongUrl(songUrl);
        song.setSongStatus(SongStatus.NORMAL);
        songMapper.insert(song);
        return new SongView(song);
    }

    @Override
    public boolean deleteSong(int channelID, int songID) {
        return songMapper.deleteByID(songID) >= 0;
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
    public boolean isChannelStatusChanged(int channelID, String token) {
        return channelStatus.isChanged(channelID, token);
    }

    @Override
    public boolean hasNewsInChannel(int channelID, String token) {
        return !channelEventService.isEmpty(TomuUtils.getNickname(token)) ;
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
    public PlayStatusView getNewPlayStatus(int channelID, String token) {
        PlayStatusView playStatusView = new PlayStatusView();
        playStatusView.setSongID(channelStatus.getLastSongID(channelID));
        playStatusView.setPosition(channelStatus.getLastPosition(channelID));
        channelStatus.fire(channelID, token);
        return playStatusView;
    }

    @Override
    public boolean changeChannelStatus(int channelID, int songID, double position, String token) {
        Date now = LeferDate.today();
        //记入事件队列，准备对外广播
        ChannelEvent.Builder<ChannelPlayStatusChangeEventDetail> builder = new ChannelEvent.Builder<>();
        ChannelPlayStatusChangeEventDetail detail = new ChannelPlayStatusChangeEventDetail();
        detail.setChannelID(channelID);
        detail.setDate(now);
        detail.setPosition(position);
        detail.setSongID(songID);
        //查找同频道的其他用户
        List<String> audience = onlineStatus.getAudience(channelID);
        audience.stream()
                .filter(aud -> !aud.equals(TomuUtils.getNickname(token)))
                .forEach(aud -> channelEventService.add(aud, builder.withType(ChannelEventType.CHANGE_PLAY_STATUS).withDetail(detail).build()));
        //记入持久化队列，交由异步线程持久化
        PlayHistory playHistory = new PlayHistory();
        playHistory.setSongID(songID);
        playHistory.setChannelID(channelID);
        playHistory.setLastPosition(position);
        playHistory.setPlayDate(now);
        messagePool.getMessageProducer().onData("insert", PlayHistory.class.getName(), playHistory);
        return true;
    }
}
