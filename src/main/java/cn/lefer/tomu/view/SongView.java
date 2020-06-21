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
    String songURL;
    SongSource songSource;

    public SongView(Song song) {
        this.songID = song.getSongID();
        this.songURL = song.getSongURL();
        this.songSource = song.getSongSource();
    }
}
