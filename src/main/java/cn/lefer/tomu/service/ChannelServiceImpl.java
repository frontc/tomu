package cn.lefer.tomu.service;

import cn.lefer.tomu.constant.SongSource;
import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.entity.Song;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.SongView;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/16
 * @Description : 频道服务的一个实现
 */
@Service
public class ChannelServiceImpl implements ChannelService{
    @Override
    public Channel createChannel() {
        Song song = new Song();
        song.setChannelID(520);
        song.setSongDuration(132);
        song.setSongID(1234);
        song.setSongName("Car Park");
        song.setSongSource(SongSource.NETEASE);
        song.setSongUrl("https://music.163.com/#/song?id=1307473639");

        Channel channel = new Channel();
        channel.setChannelID(520);
        channel.setChannelCreateDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        channel.setCurrentSong(song);
        channel.setPosition(23);
        return channel;
    }

    @Override
    public ChannelView getChannel(int channelID) {
        return new ChannelView(createChannel());
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
        List<SongView> songViews = new ArrayList<>();
        songViews.add(new SongView(new Song()));
        return songViews;
    }

    @Override
    public boolean isChannelStatusChanged() {
        return true;
    }
}
