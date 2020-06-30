package cn.lefer.tomu.cache;

import cn.lefer.tomu.entity.PlayHistory;
import cn.lefer.tomu.exception.BizErrorCode;
import cn.lefer.tomu.exception.BizRestException;
import cn.lefer.tomu.mapper.PlayHistoryMapper;
import cn.lefer.tools.Date.LeferDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/30
 * @Description : 频道状态缓存
 */
@Component
public class ChannelStatus {
    //channelID,PlayStatus
    ConcurrentHashMap<Integer,PlayStatus> channelStatusMap;
    private final PlayHistoryMapper playHistoryMapper;

    @Autowired
    public ChannelStatus(PlayHistoryMapper playHistoryMapper){
        channelStatusMap=new ConcurrentHashMap<>();
        this.playHistoryMapper=playHistoryMapper;
    }

    /*
    * 接收到change请求后，首先修改数据库的playHistory，然后修改缓存
    * */
    public boolean changeChannelStatus(int channelID,int songID,int position,String token){
        //如果当前频道的最新一条播放记录即为同一首歌，那么就更新位置和时间
        try{
            Date now = LeferDate.today();
            PlayHistory lastPlayHistory = playHistoryMapper.selectPlayStatusByChannelID(channelID);
            if(lastPlayHistory==null||lastPlayHistory.getSongID()!=songID){
                //如果当前频道的最新一条播放记录不是这首歌，那么插入一条新的播放记录
                PlayHistory playHistory = new PlayHistory();
                playHistory.setSongID(songID);
                playHistory.setChannelID(channelID);
                playHistory.setLastPosition(position);
                playHistory.setPlayDate(now);
                playHistoryMapper.insert(playHistory);
            }else{
                playHistoryMapper.updateStatus(position,now ,lastPlayHistory.getPlayHistoryID());
            }
            //写入缓存
            PlayStatus playStatus = new PlayStatus();
            playStatus.setSongID(songID);
            playStatus.setPosition(position);
            playStatus.setChangeDate(now);
            Set<String> users = new HashSet<>();
            users.add(token);
            playStatus.setBroadcast(users);
            channelStatusMap.put(channelID,playStatus);
        }catch (Exception ex){
            throw new BizRestException(BizErrorCode.PERSISTENCE_FAILED);
        }
        return true;
    }

    public boolean isChanged(int channelID,String token){
        return channelStatusMap.get(channelID)!=null&&!channelStatusMap.get(channelID).getBroadcast().contains(token);
    }

    public int getLastSongID(int channelID){
        return channelStatusMap.get(channelID).getSongID();
    }
    public int getLastPosition(int channelID){
        return channelStatusMap.get(channelID).getPosition();
    }

    public void fire(int channelID,String token){
        PlayStatus playStatus = channelStatusMap.get(channelID);
        Set<String> broadcast = playStatus.getBroadcast();
        broadcast.add(token);
        playStatus.setBroadcast(broadcast);
        channelStatusMap.put(channelID,playStatus);
    }

    private class PlayStatus{
        int songID;
        int position;
        Date changeDate;
        Set<String> broadcast;

        public int getSongID() {
            return songID;
        }

        public void setSongID(int songID) {
            this.songID = songID;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public Date getChangeDate() {
            return changeDate;
        }

        public void setChangeDate(Date changeDate) {
            this.changeDate = changeDate;
        }

        public Set<String> getBroadcast() {
            return broadcast;
        }

        public void setBroadcast(Set<String> broadcast) {
            this.broadcast = broadcast;
        }
    }
}
