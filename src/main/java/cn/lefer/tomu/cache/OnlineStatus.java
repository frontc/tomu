package cn.lefer.tomu.cache;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/29
 * @Description : 用户在线状态保持
 */
@Component
public class OnlineStatus {
    /*
    * 1.第一次访问时，如果检测到用户标识符为空，需要前端申领标识符
    * 2.以后每次访问需要带着标识符
    * 3.1用户进行频道相关操作后记录用户与频道的关系。
    * 3.2如果本次用户标识符在频道状态映射里，则只更新时间。
    * 3.3如果本次用户标识符在记录时发现该频道已经有两个额外的标识符存在，踢出10分钟之内无活跃动作的标识符。
    * 3.4如果都在30分钟内有活跃，报频道已满，可由现在在线的用户手动踢掉一个
    * */
    HashMap<Integer, Map<String, Date>> channelStatusMap;

    public OnlineStatus() {
        channelStatusMap=new HashMap<>();
    }
}
