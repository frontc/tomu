package cn.lefer.tomu.event.detail;

import cn.lefer.tomu.view.SongView;

import java.util.Date;

public class AddSongEventDetail extends AbstractChannelEventDetail{
    SongView songView;
    Date date;

    public SongView getSongView() {
        return songView;
    }

    public void setSongView(SongView songView) {
        this.songView = songView;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "AddSongEventDetail{" +
                "songView=" + songView +
                ", date=" + date +
                ", channelID=" + channelID +
                '}';
    }
}
