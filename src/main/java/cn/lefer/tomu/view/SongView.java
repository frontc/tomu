package cn.lefer.tomu.view;

import cn.lefer.tomu.constant.SongSource;
import cn.lefer.tomu.entity.Song;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/19
 * @Description : 歌曲视图
 */
public class SongView {
    int songID;
    SongSource songSource;
    String songUrl;
    String songName;
    String artistName;
    String coverUrl;
    String lrcUrl;
    String mp3Url;


    public SongView(Song song) {
        this.songID = song.getSongID();
        this.songUrl = song.getSongUrl();
        this.songSource = song.getSongSource();
        this.songName=song.getSongName();
        this.artistName=song.getArtistName();
        this.coverUrl=song.getCoverUrl();
        this.lrcUrl=song.getLrcUrl();
        this.mp3Url=song.getMp3Url();
    }

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

    @Override
    public String toString() {
        return "SongView{" +
                "songID=" + songID +
                ", songSource=" + songSource +
                ", songUrl='" + songUrl + '\'' +
                ", songName='" + songName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", lrcUrl='" + lrcUrl + '\'' +
                ", mp3Url='" + mp3Url + '\'' +
                '}';
    }
}
