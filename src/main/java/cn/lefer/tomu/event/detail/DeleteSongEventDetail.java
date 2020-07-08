package cn.lefer.tomu.event.detail;

public class DeleteSongEventDetail extends AbstractChannelEventDetail{
    int songID;

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
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
