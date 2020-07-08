package cn.lefer.tomu.event;

import cn.lefer.tomu.event.ChannelEvent;
import cn.lefer.tomu.event.detail.AbstractChannelEventDetail;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/7
 * @Description : 频道事件处理类的接口定义
 */
public interface ChannelEventService {
    /*添加一个事件*/
    void add(String key, ChannelEvent<? extends AbstractChannelEventDetail> channelEvent);
    /*消费一个事件*/
    ChannelEvent<? extends AbstractChannelEventDetail> get(String key);
    /*探测是否有待处理的事件*/
    boolean isEmpty(String key);
    /*向频道下的其他用户发送一个广播*/
    void broadcast(int channelID, String currentToke, ChannelEvent<? extends AbstractChannelEventDetail> channelEvent);
}
