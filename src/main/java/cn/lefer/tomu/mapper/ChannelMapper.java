package cn.lefer.tomu.mapper;

import cn.lefer.tomu.entity.Channel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/24
 * @Description : channel mapper
 */
@Repository
@Mapper
public interface ChannelMapper {
    int insert(Channel channel);
}
