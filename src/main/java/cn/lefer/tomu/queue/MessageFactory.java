package cn.lefer.tomu.queue;

import com.lmax.disruptor.EventFactory;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/4
 * @Description : 消息工厂类
 */
public class MessageFactory implements EventFactory<MessageEvent> {
    @Override
    public MessageEvent newInstance() {
        return new MessageEvent();
    }
}
