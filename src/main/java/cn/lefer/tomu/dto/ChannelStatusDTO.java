package cn.lefer.tomu.dto;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/30
 * @Description :
 */
public class ChannelStatusDTO {
    int songID;
    double position;

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
}
