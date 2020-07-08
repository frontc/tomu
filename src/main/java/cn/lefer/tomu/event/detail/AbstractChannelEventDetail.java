package cn.lefer.tomu.event.detail;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/7
 * @Description : 频道事件详情的抽象类
 */
public abstract class AbstractChannelEventDetail {
    int channelID;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
    Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }
}
