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
    String songUrl;
    SongSource songSource;

    public SongView(Song song) {
        this.songID = song.getSongID();
        this.songUrl = song.getSongUrl();
        this.songSource = song.getSongSource();
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public String getSongUrl() {
        return songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public SongSource getSongSource() {
        return songSource;
    }

    public void setSongSource(SongSource songSource) {
        this.songSource = songSource;
    }

    @Override
    public String toString() {
        return "SongView{" +
                "songID=" + songID +
                ", songUrl='" + songUrl + '\'' +
                ", songSource=" + songSource +
                '}';
    }
}
