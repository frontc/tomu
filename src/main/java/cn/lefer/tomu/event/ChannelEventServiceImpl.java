package cn.lefer.tomu.event;

import cn.lefer.tomu.event.detail.AbstractChannelEventDetail;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/7
 * @Description : 频道事件处理类
 */
@Component
public class ChannelEventServiceImpl implements ChannelEventService {
    final Log log = LogFactory.getLog(this.getClass());
    private ConcurrentHashMap<String, Queue<ChannelEvent<? extends AbstractChannelEventDetail>>> cache;

    public ChannelEventServiceImpl() {
        cache = new ConcurrentHashMap<>();
    }

    @Override
    public void add(String key, ChannelEvent<? extends AbstractChannelEventDetail> channelEvent) {
        if (cache.get(key) == null) {
            Queue<ChannelEvent<? extends AbstractChannelEventDetail>> queue = new LinkedList<>();
            queue.offer(channelEvent);
            cache.put(key, queue);
        } else {
            cache.get(key).offer(channelEvent);
        }
        log.debug("新增事件:"+key+"-"+channelEvent.toString());
    }

    @Override
    public ChannelEvent<? extends AbstractChannelEventDetail> get(String key) {
        if (cache.get(key) == null) {
            return null;
        } else {
            log.debug("事件弹出："+key+"-"+cache.get(key).peek());
            return cache.get(key).poll();
        }
    }

    @Override
    public int size(String key) {
        if (cache.get(key) == null) {
            return 0;
        } else {
            return cache.get(key).size();
        }
    }
}
