package cn.lefer.tomu.service;

import cn.lefer.tomu.constant.SongStatus;
import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.entity.PlayHistory;
import cn.lefer.tomu.entity.Song;
import cn.lefer.tomu.mapper.ChannelMapper;
import cn.lefer.tomu.mapper.PlayHistoryMapper;
import cn.lefer.tomu.mapper.SongMapper;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.SongView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
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
    public Channel createChannel() {
        Channel channel = new Channel();
        channel.setChannelCreateDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        channelMapper.insert(channel);
        return channel;
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
    public Channel addSong(Song song) {
        return null;
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
