package cn.lefer.tomu.entity;

import java.util.Date;

/**
 * @author : lefer
 * @version V1.0
 * @date :   2020/6/15
 * @Description : 播放历史实体类
 */
public class PlayHistory {
    int playHistoryID;
    int channelID;
    int songID;
    int lastPosition;
    Date playDate;

    public int getPlayHistoryID() {
        return playHistoryID;
    }

    public void setPlayHistoryID(int playHistoryID) {
        this.playHistoryID = playHistoryID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public int getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(int lastPosition) {
        this.lastPosition = lastPosition;
    }

    public Date getPlayDate() {
        return playDate;
    }

    public void setPlayDate(Date playDate) {
        this.playDate = playDate;
    }

    @Override
    public String toString() {
        return "PlayHistory{" +
                "playHistoryID=" + playHistoryID +
                ", channelID=" + channelID +
                ", songID=" + songID +
                ", lastPosition=" + lastPosition +
                ", playDate=" + playDate +
                '}';
    }
}
