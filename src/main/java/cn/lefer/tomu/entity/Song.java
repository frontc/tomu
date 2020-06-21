package cn.lefer.tomu.entity;

import cn.lefer.tomu.constant.SongSource;

/**
 * @author : lefer
 * @version V1.0
 * @date :   2020/6/15
 * @Description : 歌曲实体类
 */
public class Song {
    int songID;
    int channelID;
    String songName;
    String songURL;
    SongSource songSource;
    String songStatus;
    int songDuration;

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSongURL() {
        return songURL;
    }

    public void setSongURL(String songURL) {
        this.songURL = songURL;
    }

    public SongSource getSongSource() {
        return songSource;
    }

    public void setSongSource(SongSource songSource) {
        this.songSource = songSource;
    }

    public String getSongStatus() {
        return songStatus;
    }

    public void setSongStatus(String songStatus) {
        this.songStatus = songStatus;
    }

    public int getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }
}
