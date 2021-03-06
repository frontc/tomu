package cn.lefer.tomu.mapper;

import cn.lefer.tomu.constant.SongStatus;
import cn.lefer.tomu.entity.Song;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/28
 * @Description : Song Mapper
 */
@Mapper
@Repository
public interface SongMapper {
    Song selectByID(@Param("songID") int songID);

    Song selectBySongNameAndArtistNameOrMP3Url(@Param("songName")  String songName, @Param("artistName") String artistName,@Param("mp3Url") String mp3Url);

    int insert(Song song);

    List<Song> selectByChannelID(@Param("channelID") int channelID, @Param("songStatusList") List<SongStatus> songStatusList, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);

    List<Song> selectAllByChannelID(@Param("channelID") int channelID, @Param("songStatusList") List<SongStatus> songStatusList);

    int countByChannelID(@Param("channelID") int channelID, @Param("songStatusList") List<SongStatus> songStatusList);

    //改用ChannelSongRelMapper.delete()
    @Deprecated
    int deleteByID(@Param("songID") int songID);

    int batchUpdateSongStatus(@Param("songStatus") SongStatus songStatus,@Param("songIDs") List<Integer> songIDs);

    List<Song> selectAll(@Param("songStatusList") List<SongStatus> songStatusList);

    int countByChannelIDAndMP3Url(@Param("channelID") int channelID,@Param("songStatusList") List<SongStatus> songStatusList,@Param("mp3Url") String url);

    /*随机选取指定数量的歌曲*/
    List<Song> randomSelect(@Param("songStatus") SongStatus songStatus,@Param("size") int size);
}
