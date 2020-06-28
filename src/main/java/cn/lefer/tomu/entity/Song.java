package cn.lefer.tomu.entity;

import cn.lefer.tomu.constant.SongSource;
import cn.lefer.tomu.constant.SongStatus;

import java.util.Date;

/**
 * @author : lefer
 * @version V1.0
 * @date :   2020/6/15
 * @Description : 歌曲实体类
 */
public class Song {
    int songID;//歌曲ID
    int channelID;//频道ID
    String songName;//歌曲名称
    String artistName;//歌手名称
    String coverUrl;//封面URL
    String lrcUrl;//歌词URL
    String mp3Url; //mp3文件的地址
    int songDuration;//歌曲时长
    SongSource songSource;//歌曲来源平台
    String songUrl; //歌曲的来源地址
    SongStatus songStatus;
    Date songAddDate;

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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getLrcUrl() {
        return lrcUrl;
    }

    public void setLrcUrl(String lrcUrl) {
        this.lrcUrl = lrcUrl;
    }

    public String getMp3Url() {
        return mp3Url;
    }

    public void setMp3Url(String mp3Url) {
        this.mp3Url = mp3Url;
    }

    public int getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(int songDuration) {
        this.songDuration = songDuration;
    }

    public SongSource getSongSource() {
        return songSource;
    }

    public void setSongSource(SongSource songSource) {
        this.songSource = songSource;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public SongStatus getSongStatus() {
        return songStatus;
    }

    public void setSongStatus(SongStatus songStatus) {
        this.songStatus = songStatus;
    }

    public Date getSongAddDate() {
        return songAddDate;
    }

    public void setSongAddDate(Date songAddDate) {
        this.songAddDate = songAddDate;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songID=" + songID +
                ", channelID=" + channelID +
                ", songName='" + songName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", lrcUrl='" + lrcUrl + '\'' +
                ", mp3Url='" + mp3Url + '\'' +
                ", songDuration=" + songDuration +
                ", songSource=" + songSource +
                ", songUrl='" + songUrl + '\'' +
                ", songStatus='" + songStatus + '\'' +
                ", songAddDate=" + songAddDate +
                '}';
    }
}
