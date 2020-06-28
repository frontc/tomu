package cn.lefer.tomu;

import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.mapper.ChannelMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/24
 * @Description : 频道mapper接口测试用例
 */
@SpringBootTest
public class ChannelMapperTests {
    @Autowired
    ChannelMapper channelMapper;

    @Test
    public void testInsert(){
        Channel channel = new Channel();
        channel.setChannelCreateDate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        channelMapper.insert(channel);
        Assertions.assertTrue(channel.getChannelID()>0);
    }
    @Test
    public void testSelectByID(){
        Channel channel = channelMapper.selectByID(1);
        System.out.println(channel);
        Assertions.assertNotNull(channel);
    }
}
