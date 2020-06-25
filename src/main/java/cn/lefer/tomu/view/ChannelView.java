package cn.lefer.tomu.view;

import cn.lefer.tomu.entity.Channel;

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
    PlayStatusView playStatus;

    public ChannelView(Channel channel) {
        this.channelID = channel.getChannelID();
        this.channelName = channel.getChannelName();
        this.channelCreateDate = channel.getChannelCreateDate();
//        this.playStatus= new PlayStatusView(channel.getCurrentSong().getSongID(),channel.getPosition());
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public int getChannelName() {
        return channelName;
    }

    public void setChannelName(int channelName) {
        this.channelName = channelName;
    }

    public Date getChannelCreateDate() {
        return channelCreateDate;
    }

    public void setChannelCreateDate(Date channelCreateDate) {
        this.channelCreateDate = channelCreateDate;
    }

    public PlayStatusView getPlayStatus() {
        return playStatus;
    }

    public void setPlayStatus(PlayStatusView playStatus) {
        this.playStatus = playStatus;
    }

    @Override
    public String toString() {
        return "ChannelView{" +
                "channelID=" + channelID +
                ", channelName=" + channelName +
                ", channelCreateDate=" + channelCreateDate +
                ", playStatus=" + playStatus +
                '}';
    }
}
