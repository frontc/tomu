package cn.lefer.tomu.view;

import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.entity.Song;

import java.util.Date;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/19
 * @Description : 频道的视图类
 */
public class ChannelView {
    int channelID;
    int channelName;
    Date channelCreateDate;
    Song currentSong;
    int position;

    public ChannelView(Channel channel) {
        this.channelID = channel.getChannelID();
        this.channelName = channel.getChannelName();
        this.channelCreateDate = channel.getChannelCreateDate();
        this.currentSong = channel.getCurrentSong();
        this.position = channel.getPosition();
    }
}
