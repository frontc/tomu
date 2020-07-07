package cn.lefer.tomu.event.detail;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/7
 * @Description : 频道事件详情的抽象类
 */
public abstract class AbstractChannelEventDetail {
    int channelID;

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }
}
