package cn.lefer.tomu.dto;

import cn.lefer.tomu.constant.SongSource;
import cn.lefer.tomu.constant.SongStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/18
 * @Description : 播放历史数据传输类
 */
public class PlayHistoryDTO {
    int songID;
    SongSource songSource;
    String songUrl;
    String songName;
    String artistName;
    String coverUrl;
    String lrcUrl;
    String mp3Url;
    SongStatus songStatus;
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss", timezone="GMT+8")
    Date playDate;

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
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

    public SongStatus getSongStatus() {
        return songStatus;
    }

    public void setSongStatus(SongStatus songStatus) {
        this.songStatus = songStatus;
    }

    public Date getPlayDate() {
        return playDate;
    }

    public void setPlayDate(Date playDate) {
        this.playDate = playDate;
    }

    @Override
    public String toString() {
        return "PlayHistoryDTO{" +
                "songID=" + songID +
                ", songSource=" + songSource +
                ", songUrl='" + songUrl + '\'' +
                ", songName='" + songName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", lrcUrl='" + lrcUrl + '\'' +
                ", mp3Url='" + mp3Url + '\'' +
                ", songStatus=" + songStatus +
                ", playDate=" + playDate +
                '}';
    }
}
