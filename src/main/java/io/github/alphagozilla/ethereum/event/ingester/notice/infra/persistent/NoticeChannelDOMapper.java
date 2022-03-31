package io.github.alphagozilla.ethereum.event.ingester.notice.infra.persistent;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 11:03
 */
@Mapper
public interface NoticeChannelDOMapper extends BaseMapper<NoticeChannelDO> {
}
