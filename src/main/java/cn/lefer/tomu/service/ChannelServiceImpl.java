package cn.lefer.tomu.service;

import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.entity.Song;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.SongView;
import org.springframework.stereotype.Service;

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
        return new Channel();
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
