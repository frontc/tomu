package cn.lefer.tomu.service;

import cn.lefer.tomu.constant.SongSource;
import cn.lefer.tomu.entity.Channel;
import cn.lefer.tomu.view.ChannelView;
import cn.lefer.tomu.view.Page;
import cn.lefer.tomu.view.PlayStatusView;
import cn.lefer.tomu.view.SongView;

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
    ChannelView getChannel(int channelID);

    //增加歌曲
    SongView addSong(int channelID,
                     String songName,
                     String artistName,
                     String coverUrl,
                     String lrcUrl,
                     String mp3Url,
                     double songDuration,
                     SongSource songSource,
                     String songUrl);

    //删除歌曲
    boolean deleteSong(int channelID, int songID);

    //播放歌曲
    Channel playSong(int channelID, int songID);

    //获得歌单
    Page<SongView> getSongs(int channelID, int pageNum, int pageSize);

    //歌单的状态是否发生变化
    boolean isChannelStatusChanged(int channelID, String token);

    PlayStatusView getNewPlayStatus(int channelID, String token);

    boolean changeChannelStatus(int channelID, int songID, double position, String token);
}
