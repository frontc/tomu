package cn.lefer.tomu.event.detail;

import java.util.Date;

public class DeleteSongEventDetail extends AbstractChannelEventDetail{
    int songID;
    Date date;

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DeleteSongEventDetail{" +
                "songID=" + songID +
                ", date=" + date +
                ", channelID=" + channelID +
                '}';
    }
}
