package cn.lefer.tomu.event.detail;

public class EnterChannelEventDetail extends AbstractChannelEventDetail{
    String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "EnterChannelEventDetail{" +
                "nickName='" + nickName + '\'' +
                ", date=" + date +
                ", channelID=" + channelID +
                '}';
    }
}
