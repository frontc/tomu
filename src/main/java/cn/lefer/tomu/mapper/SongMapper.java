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
    int insert(Song song);
    List<Song> selectByChannelID(@Param("channelID") int channelID, @Param("songStatusList")List<SongStatus> songStatusList, @Param("pageNum") int pageNum, @Param("pageSize") int pageSize);
    int countByChannelID(@Param("channelID") int channelID, @Param("songStatusList")List<SongStatus> songStatusList);
    int deleteByID(int songID);
}
