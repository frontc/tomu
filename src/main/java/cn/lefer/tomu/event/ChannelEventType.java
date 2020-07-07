package cn.lefer.tomu.event;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/19
 * @Description : 歌曲来源
 */
public enum ChannelEventType {
    ADD_SONG,/*增加歌曲*/
    DELETE_SONG,/*删除歌曲*/
    AUDIENCE_IN,/*观众进入*/
    AUDIENCE_OUT,/*观众退出*/
    CHANGE_PLAY_STATUS,/*改变播放状态*/
}
