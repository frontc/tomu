package cn.lefer.tomu.other;

import cn.lefer.tomu.schedule.ScheduleCenter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/8
 * @Description :
 */
@SpringBootTest
public class ScheduleCenterTests {
    @Autowired
    ScheduleCenter scheduleCenter;

    @Test
    public void testMp3Check(){
        scheduleCenter.mp3Check();
        Assertions.assertTrue(true);
    }
}
