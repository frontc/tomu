package cn.lefer.tomu.mapper;

import cn.lefer.tomu.entity.ChannelSongRel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/7/12
 * @Description :
 */
@Repository
@Mapper
public interface ChannelSongRelMapper {
    int insert(ChannelSongRel channelSongRel);
    int delete(@Param("channelID") int channelID, @Param("songID") long songID);
    int existsRelWithChannelIDAndSongID(@Param("channelID") int channelID, @Param("songID") long songID);
}
