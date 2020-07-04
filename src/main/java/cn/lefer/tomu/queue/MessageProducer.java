package cn.lefer.tomu.queue;

import com.lmax.disruptor.RingBuffer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/4
 * @Description : 消息生产者
 */
public class MessageProducer {
    private final RingBuffer<MessageEvent> ringBuffer;
    private final Log log = LogFactory.getLog(this.getClass());
    public MessageProducer(RingBuffer<MessageEvent> messageEventRingBuffer){
        this.ringBuffer=messageEventRingBuffer;
    }
    public void onData(String action,String type,Object object){
        long sequence = ringBuffer.next();
        try{
            MessageEvent msg = ringBuffer.get(sequence);
            msg.setAction(action);
            msg.setType(type);
            msg.setValue(object);
            log.debug("发送消息："+msg.toString());
        }finally {
            ringBuffer.publish(sequence);
        }
    }
}
