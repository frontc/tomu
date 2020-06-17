package cn.lefer.tomu.vo;

import cn.lefer.tomu.entity.Channel;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/17
 * @Description : 播放状态
 */
public class PlayStatus {
    int songID;
    int position;


    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "PlayStatus{" +
                "songID=" + songID +
                ", position=" + position +
                '}';
    }
}
