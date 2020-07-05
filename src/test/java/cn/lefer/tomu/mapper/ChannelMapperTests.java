package cn.lefer.tomu.mapper;

import cn.lefer.tomu.entity.Channel;
import cn.lefer.tools.Date.LeferDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/24
 * @Description : 频道mapper接口测试用例
 */
@SpringBootTest
@Transactional
public class ChannelMapperTests {
    @Autowired
    ChannelMapper channelMapper;

    @Test
    public void testInsert() {
        Channel channel = init();
        channelMapper.insert(channel);
        Assertions.assertTrue(channel.getChannelID() > 0);
    }

    @Test
    public void testSelectByID() {
        Channel channel1 = init();
        channelMapper.insert(channel1);
        Channel channel2 = channelMapper.selectByID(channel1.getChannelID());
        Assertions.assertNotNull(channel2);
    }

    private Channel init() {
        Channel channel = new Channel();
        channel.setPosition(1);
        channel.setChannelName("test");
        channel.setChannelOwnerID(1);
        channel.setChannelKey("test");
        channel.setChannelCreateDate(LeferDate.today());
        return channel;
    }
}
