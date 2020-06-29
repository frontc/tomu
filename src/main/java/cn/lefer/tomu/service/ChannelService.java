package cn.lefer.tomu.service;

import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.entity.Song;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.SongView;

import java.util.List;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/16
 * @Description : 频道服务接口定义
 */
public interface ChannelService {
    //创建一个频道
    ChannelView createChannel();
    //根据channelID获取一个频道
    ChannelView getChannel(int channelID);
    //增加歌曲
    Channel addSong(Song song);
    //删除歌曲
    Channel deleteSong(int channelID,int songID);
    //播放歌曲
    Channel playSong(int channelID,int songID);
    //获得歌单
    List<SongView> getSongs(int channelID);
    //歌单的状态是否发生变化
    boolean isChannelStatusChanged();
}
