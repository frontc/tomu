package cn.lefer.tomu.event;


import cn.lefer.tomu.event.detail.ChannelPlayStatusChangeEventDetail;
import cn.lefer.tools.Date.LeferDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

@SpringBootTest
public class ChannelEventServiceTests {
    @Autowired
    ChannelEventService channelEventService;

    @Test
    public void testHashMap(){
        ConcurrentHashMap<String,String> map = new ConcurrentHashMap<String, String>();
        System.out.println(map.get("1"));
        Assertions.assertTrue(map.get("1")==null);

    }

    @Test
    public void testSize() {
        Assertions.assertEquals(0, channelEventService.size("test"));
        ChannelEvent<ChannelPlayStatusChangeEventDetail> channelEvent = init();
        channelEventService.add("test", channelEvent);
        Assertions.assertEquals(1, channelEventService.size("test"));
        channelEventService.get("test");
        Assertions.assertEquals(0, channelEventService.size("test"));
    }

    @Test
    public void testAdd() {
        ChannelEvent<ChannelPlayStatusChangeEventDetail> channelEvent = init();
        channelEventService.add("test", channelEvent);
        Assertions.assertTrue(true);
    }

    @Test
    public void testGet() {
        ChannelEvent<ChannelPlayStatusChangeEventDetail> channelEvent = init();
        channelEventService.add("test", channelEvent);
        Assertions.assertEquals(channelEventService.get("test").getType(), ChannelEventType.CHANGE_PLAY_STATUS);
    }

    private ChannelEvent<ChannelPlayStatusChangeEventDetail> init() {
        ChannelPlayStatusChangeEventDetail detail = new ChannelPlayStatusChangeEventDetail();
        detail.setChannelID(1);
        detail.setSongID(1);
        detail.setPosition(0.01);
        detail.setDate(LeferDate.today());
        ChannelEvent.Builder<ChannelPlayStatusChangeEventDetail> builder = new ChannelEvent.Builder<>();
        return builder.withType(ChannelEventType.CHANGE_PLAY_STATUS)
                .withDetail(detail).build();
    }
}
