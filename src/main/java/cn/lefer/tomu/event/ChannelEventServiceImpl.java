package cn.lefer.tomu.event;

import cn.lefer.tomu.cache.OnlineStatus;
import cn.lefer.tomu.event.detail.AbstractChannelEventDetail;
import cn.lefer.tomu.utils.TomuUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
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
    //改变思路，这里不再存放这个用户产生的事件，而是存放这个用户需要处理的事件。
    private ConcurrentHashMap<String, Queue<ChannelEvent<? extends AbstractChannelEventDetail>>> cache;
    private final OnlineStatus onlineStatus;

    @Autowired
    public ChannelEventServiceImpl(OnlineStatus onlineStatus) {
        cache = new ConcurrentHashMap<>();
        this.onlineStatus=onlineStatus;
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
    public boolean isEmpty(String key) {
        return cache.getOrDefault(key, new LinkedList<>()).peek()==null;
    }

    @Override
    public void broadcast(int channelID, String currentToke, ChannelEvent<? extends AbstractChannelEventDetail> channelEvent) {
        List<String> audience = onlineStatus.getAudienceWithNickName(channelID);
        String currentNickName = TomuUtils.getNickname(currentToke);
        audience.stream()
                .filter(aud -> !aud.equals(currentNickName))
                .forEach(aud -> add(aud, channelEvent));
    }
}
