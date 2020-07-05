package cn.lefer.tomu.mapper;

import cn.lefer.tomu.entity.PlayHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/28
 * @Description : 播放历史映射类
 */
@Mapper
@Repository
public interface PlayHistoryMapper {
    int insert(PlayHistory playHistory);

    PlayHistory selectPlayStatusByChannelID(@Param("channelID") int channelID);

    int updateStatus(@Param("lastPosition") double lastPosition, @Param("playDate") Date playDate, @Param("playHistoryID") int playHistoryID);
}
