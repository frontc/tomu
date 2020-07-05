package cn.lefer.tomu.cache;

import cn.lefer.tomu.exception.BizErrorCode;
import cn.lefer.tomu.exception.BizRestException;
import cn.lefer.tomu.utils.TomuUtils;
import cn.lefer.tools.Date.LeferDate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/29
 * @Description : 用户在线状态保持
 */
@Component
public class OnlineStatus {
    @Value("${channel.size}")
    int channelSize;
    @Value("${idle.time}")
    long idleTime;
    /*
     * 1.第一次访问时，如果检测到用户标识符为空，需要前端申领标识符
     * 2.以后每次访问需要带着标识符
     * 3.1用户进行频道相关操作后记录用户与频道的关系。
     * 3.2如果本次用户标识符在频道状态映射里，则只更新时间。
     * 3.3如果本次用户标识符在记录时发现该频道已经有两个额外的标识符存在，踢出10分钟之内无活跃动作的标识符。
     * 3.4如果都在10分钟内有活跃，报频道已满，可由现在在线的用户手动踢掉一个
     * */
    ConcurrentHashMap<Integer, HashMap<String, Date>> channelStatusMap;//频道里的访客
    ConcurrentHashMap<String, ChannelPlus> userStatusMap;//访客对应的频道，一个访客只能属于一个频道

    public OnlineStatus() {
        channelStatusMap = new ConcurrentHashMap<>();
        userStatusMap = new ConcurrentHashMap<>();
    }

    public void updateOnlineStatus(String token, int channelID) {
        Date date = LeferDate.today();
        /*先增
         * 1.如果频道在缓存中存在
         * 1.1 判断频道是否满座:如果不满座，直接坐下。
         * 1.2               如果满座，踢走超时用户坐下
         * 1.3               如果满座，且没有超时用户，那么告知用户满座
         * 2.如果频道在缓存中不存在，直接新增
         * 后删
         * 1.判断用户是否在缓存中存在
         * 1.1 如果用户在缓存中不存在，直接新增
         * 1.2 如果用户在缓存中存在，判断缓存的频道是否发生了变化，如果没发生变化，只更新时间
         * 1.3                                           如果发生了变化，则要清除旧频道再缓存
         * */
        if (channelStatusMap.containsKey(channelID)) {
            //频道有空余座位或该用户本来就在频道内，则直接添加
            if (channelStatusMap.get(channelID).size() < channelSize || channelStatusMap.get(channelID).containsKey(token)) {
                channelStatusMap.get(channelID).put(token, date);
            } else {
                //频道没有空余座位，则开始踢人
                channelStatusMap.get(channelID).forEach((k, v) -> clearChannelStatusMap(channelID, k, v));
                //踢出空座位，则更新
                if (channelStatusMap.get(channelID).size() < channelSize) {
                    channelStatusMap.get(channelID).put(token, date);
                } else {//还是无座，报座位已满
                    throw new BizRestException(BizErrorCode.CHANNEL_IS_FULL);
                }
            }
        } else {//如果频道在缓存中不存在，直接新增
            HashMap<String, Date> tokenDateMap = new HashMap<>();
            tokenDateMap.put(token, date);
            channelStatusMap.put(channelID, tokenDateMap);
        }
        ChannelPlus channelPlus = new ChannelPlus();
        channelPlus.setChannelID(channelID);
        channelPlus.setUpdateDate(date);
        //只要用户出现在其他频道中，才需要执行删除过时信息
        if (userStatusMap.containsKey(token) && userStatusMap.get(token).getChannelID() != channelID) {
            channelStatusMap.get(userStatusMap.get(token).getChannelID()).remove(token);
        }
        //将用户缓存起来
        userStatusMap.put(token, channelPlus);
    }

    private void clearChannelStatusMap(int channelID, String k, Date v) {
        Date date = LeferDate.today();
        Date dateBefore = new Date(date.getTime() - idleTime);
        if (v.before(dateBefore)) {
            channelStatusMap.get(channelID).remove(k);
        }
    }

    public List<String> getAudience(int channelID) {
        return channelStatusMap.get(channelID).keySet().stream().map(TomuUtils::getNickname).collect(Collectors.toList());
    }

    private class ChannelPlus {
        int channelID;
        Date updateDate;

        public int getChannelID() {
            return channelID;
        }

        public void setChannelID(int channelID) {
            this.channelID = channelID;
        }

        public Date getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(Date updateDate) {
            this.updateDate = updateDate;
        }
    }
}
