package cn.lefer.tomu.service;

import cn.lefer.tomu.constant.SongSource;
import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.event.ChannelEvent;
import cn.lefer.tomu.event.detail.AbstractChannelEventDetail;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.Page;
import cn.lefer.tomu.view.PlayStatusView;
import cn.lefer.tomu.view.SongView;
import org.springframework.http.codec.ServerSentEvent;

import java.io.IOException;
import java.util.List;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/16
 * @Description : 频道服务接口定义
 */
public interface ChannelService {
    //创建一个频道
    ChannelView createChannel();

    //根据channelID获取一个频道
    ChannelView getChannel(int channelID,String token);

    //增加歌曲
    SongView addSong(int channelID,
                     String token,
                     String songName,
                     String artistName,
                     String coverUrl,
                     String lrcUrl,
                     String mp3Url,
                     double songDuration,
                     SongSource songSource,
                     String songUrl) throws IOException;

    //删除歌曲
    boolean deleteSong(int channelID, int songID, String token);

    //播放歌曲
    Channel playSong(int channelID, int songID);

    //获得歌单
    Page<SongView> getSongs(int channelID, int pageNum, int pageSize);

    List<SongView> getSongs(int channelID);

    //歌单的状态是否发生变化


    boolean hasNewsInChannel(int channelID, String token);

    ServerSentEvent<ChannelEvent<? extends AbstractChannelEventDetail>> getChannelEvent(int channelID, String token, String seq);



    boolean changeChannelStatus(int channelID, int songID, double position, String token);

    //用户退出
    boolean exit(int channelID, String token);
    boolean kick(int channelID, String nickName);
    //获取频道下的听众
    List<String> getAudienceWithNickName(int channelID);
}
