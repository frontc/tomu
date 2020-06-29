package cn.lefer.tomu;

import cn.lefer.tomu.entity.PlayHistory;
import cn.lefer.tomu.mapper.PlayHistoryMapper;
import cn.lefer.tools.Date.LeferDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/28
 * @Description : 测试播放历史的持久化接口
 */
@SpringBootTest
public class PlayHistoryMapperTests {
    @Autowired
    PlayHistoryMapper playHistoryMapper;

    @Test
    public void testInsert(){
        PlayHistory playHistory = new PlayHistory();
        playHistory.setChannelID(1);
        playHistory.setPlayDate(LeferDate.today());
        playHistory.setLastPosition(123);
        playHistory.setSongID(1);
        playHistoryMapper.insert(playHistory);
        Assertions.assertTrue(playHistory.getPlayHistoryID()>0);
    }

    @Test
    public void testSelectPlayStatusByChannelID(){
        PlayHistory playHistory = playHistoryMapper.selectPlayStatusByChannelID(1);
        System.out.println(playHistory);
        Assertions.assertNotNull(playHistory);
    }
}
