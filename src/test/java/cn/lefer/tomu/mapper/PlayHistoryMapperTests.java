package cn.lefer.tomu.mapper;

import cn.lefer.tomu.entity.PlayHistory;
import cn.lefer.tools.Date.LeferDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/28
 * @Description : 测试播放历史的持久化接口
 */
@SpringBootTest
@Transactional
public class PlayHistoryMapperTests {
    @Autowired
    PlayHistoryMapper playHistoryMapper;

    @Test
    public void testInsert() {
        PlayHistory playHistory = init();
        playHistoryMapper.insert(playHistory);
        Assertions.assertTrue(playHistory.getPlayHistoryID() > 0);
    }

    @Test
    public void testSelectPlayStatusByChannelID() {
        PlayHistory playHistory1 = init();
        playHistoryMapper.insert(playHistory1);
        PlayHistory playHistory2 = playHistoryMapper.selectPlayStatusByChannelID(playHistory1.getChannelID());
        Assertions.assertNotNull(playHistory2);
    }

    @Test
    public void testUpdateStatus() {
        PlayHistory playHistory = init();
        playHistoryMapper.insert(playHistory);
        int i = playHistoryMapper.updateStatus(1, LeferDate.today(), playHistory.getPlayHistoryID());
        Assertions.assertEquals(1, i);
    }

    private PlayHistory init() {
        PlayHistory playHistory = new PlayHistory();
        playHistory.setChannelID(1);
        playHistory.setPlayDate(LeferDate.today());
        playHistory.setLastPosition(123);
        playHistory.setSongID(1);
        return playHistory;
    }
}
