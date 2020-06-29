package cn.lefer.tomu.service;

import cn.lefer.tomu.constant.SongSource;
import cn.lefer.tomu.constant.SongStatus;
import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.entity.PlayHistory;
import cn.lefer.tomu.entity.Song;
import cn.lefer.tomu.mapper.ChannelMapper;
import cn.lefer.tomu.mapper.PlayHistoryMapper;
import cn.lefer.tomu.mapper.SongMapper;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.SongView;
import cn.lefer.tools.Date.LeferDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
public class ChannelServiceImpl implements ChannelService{
    ChannelMapper channelMapper;
    PlayHistoryMapper playHistoryMapper;
    SongMapper songMapper;

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
        if(channel==null) return null;
        PlayHistory playHistory = playHistoryMapper.selectPlayStatusByChannelID(channelID);
        if(playHistory!=null){
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
                           int songDuration,
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
    public Channel deleteSong(int channelID, int songID) {
        return null;
    }

    @Override
    public Channel playSong(int channelID, int songID) {
        return null;
    }

    @Override
    public List<SongView> getSongs(int channelID) {
        List<SongStatus> songStatusList = new ArrayList<>();
        songStatusList.add(SongStatus.NORMAL);
        songStatusList.add(SongStatus.OUTDATE);
        List<Song> songs = songMapper.selectByChannelID(channelID,songStatusList);
        return songs.stream().map(SongView::new).collect(Collectors.toList());
    }

    @Override
    public boolean isChannelStatusChanged() {
        return true;
    }

    @Autowired
    public void setChannelMapper(ChannelMapper channelMapper) {
        this.channelMapper = channelMapper;
    }

    @Autowired
    public void setPlayHistoryMapper(PlayHistoryMapper playHistoryMapper) {
        this.playHistoryMapper = playHistoryMapper;
    }

    @Autowired
    public void setSongMapper(SongMapper songMapper) {
        this.songMapper = songMapper;
    }
}
