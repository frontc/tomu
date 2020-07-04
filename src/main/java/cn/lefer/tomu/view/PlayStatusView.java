package cn.lefer.tomu.view;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/17
 * @Description : 播放状态
 */
public class PlayStatusView {
    int songID;
    double position;

    public PlayStatusView(int songID, double position) {
        this.songID = songID;
        this.position = position;
    }

    public PlayStatusView() {
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
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
