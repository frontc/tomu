package cn.lefer.tomu.event.detail;

import java.util.Date;

public class ChannelPlayStatusChangeEventDetail extends AbstractChannelEventDetail{
    int songID;
    double position;
    Date date;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
