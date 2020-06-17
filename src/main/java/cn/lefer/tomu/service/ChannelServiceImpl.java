package cn.lefer.tomu.service;

import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.entity.Song;
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
    public Channel getChannel(int channelID) {
        return createChannel();
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
    public List<Song> getSongs(int channelID) {
        return new ArrayList<>();
    }
}
