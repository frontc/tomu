package cn.lefer.tomu.event.detail;

public class ChannelPlayStatusChangeEventDetail extends AbstractChannelEventDetail{
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

    @Override
    public String toString() {
        return "ChannelPlayStatusChangeEventDetail{" +
                "channelID=" + channelID +
                ", songID=" + songID +
                ", position=" + position +
                ", date=" + date +
                '}';
    }
}
