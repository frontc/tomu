package cn.lefer.tomu.service;

import cn.lefer.tomu.mapper.ChannelMapper;
import cn.lefer.tomu.mapper.PlayHistoryMapper;
import cn.lefer.tomu.mapper.SongMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/5
 * @Description : 频道服务单元测试用例
 */
@ExtendWith(MockitoExtension.class)
public class ChannelServiceTests {
    @InjectMocks
    ChannelServiceImpl channelService;

    @Mock
    ChannelMapper channelMapper;
    @Mock
    PlayHistoryMapper playHistoryMapper;
    @Mock
    SongMapper songMapper;

    @Test
    public void testCreateChannel() {
        Mockito.doReturn(1).when(channelMapper).insert(any());
        Assertions.assertNotNull(channelService.createChannel());
    }

    @Test
    public void testGetChannel() {

    }
}
