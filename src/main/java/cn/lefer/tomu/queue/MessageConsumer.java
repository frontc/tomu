package cn.lefer.tomu.queue;

import cn.lefer.tomu.entity.PlayHistory;
import cn.lefer.tomu.mapper.PlayHistoryMapper;
import cn.lefer.tomu.utils.SpringUtils;
import com.lmax.disruptor.EventHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/4
 * @Description : 消息消费者
 */
public class MessageConsumer implements EventHandler<MessageEvent> {
    private final Log log = LogFactory.getLog(this.getClass());

    @Override
    public void onEvent(MessageEvent messageEvent, long l, boolean b) throws Exception {
        if (messageEvent.getValue() instanceof PlayHistory) {
            PlayHistoryMapper playHistoryMapper = SpringUtils.getBean(PlayHistoryMapper.class);
            PlayHistory playHistory = (PlayHistory) messageEvent.getValue();
            PlayHistory lastPlayHistory = playHistoryMapper.selectPlayStatusByChannelID(playHistory.getChannelID());
            if (lastPlayHistory == null || lastPlayHistory.getSongID() != playHistory.getSongID()) {
                playHistoryMapper.insert(playHistory);
            } else {
                playHistoryMapper.updateStatus(playHistory.getLastPosition(), playHistory.getPlayDate(), lastPlayHistory.getPlayHistoryID());
            }
            log.debug("消息消费成功：" + messageEvent.getValue());
        } else {
            log.error("无法识别的消息类型，消息处理失败:" + messageEvent.getAction() + "-" + messageEvent.getType());
        }
    }
}
