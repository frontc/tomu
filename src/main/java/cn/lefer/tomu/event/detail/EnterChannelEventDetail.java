package cn.lefer.tomu.event.detail;

import java.util.Date;

public class EnterChannelEventDetail extends AbstractChannelEventDetail{
    String nickName;
    Date date;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
