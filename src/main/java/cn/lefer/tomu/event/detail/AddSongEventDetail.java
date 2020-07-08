package cn.lefer.tomu.event.detail;

import cn.lefer.tomu.view.SongView;

public class AddSongEventDetail extends AbstractChannelEventDetail{
    SongView songView;

    public SongView getSongView() {
        return songView;
    }

    public void setSongView(SongView songView) {
        this.songView = songView;
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
