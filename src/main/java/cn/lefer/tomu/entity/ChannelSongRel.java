package cn.lefer.tomu.entity;

import java.util.Date;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/12
 * @Description : 频道下的歌曲
 */
public class ChannelSongRel {
    long channelSongRelID;
    int channelID;
    long songID;
    Date addDate;
    boolean validFlag;

    public ChannelSongRel(int channelID, long songID, Date addDate, boolean validFlag) {
        this.channelID = channelID;
        this.songID = songID;
        this.addDate = addDate;
        this.validFlag = validFlag;
    }

    public boolean isValidFlag() {
        return validFlag;
    }

    public void setValidFlag(boolean validFlag) {
        this.validFlag = validFlag;
    }

    public long getChannelSongRelID() {
        return channelSongRelID;
    }

    public void setChannelSongRelID(long channelSongRelID) {
        this.channelSongRelID = channelSongRelID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public long getSongID() {
        return songID;
    }

    public void setSongID(long songID) {
        this.songID = songID;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }
}
