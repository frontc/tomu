package cn.lefer.tomu;

import cn.lefer.tomu.cache.ChannelStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/4
 * @Description :
 */
@SpringBootTest
public class ChannelStatusTests {
    @Autowired
    ChannelStatus channelStatus;
    @Test
    public void testIsChanged(){
        int channelID=1;
        int songID=2;
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxNzIuMTguMC4xIiwic3ViIjoidG9tdSIsImlzcyI6ImxlZmVyIiwiaWF0IjoxNTkzNzkzMDc2LCJleHAiOjE1OTQ2NTcwNzZ9.yJwthWDwp7TusuDgpJGG8-SOSEHa3jkw2aMm6sEpmpw";
        System.out.println(channelStatus.isChanged(channelID,token));
        channelStatus.changeChannelStatus(channelID,songID,123,token);
        System.out.println(channelStatus.isChanged(channelID,token));
    }
}
