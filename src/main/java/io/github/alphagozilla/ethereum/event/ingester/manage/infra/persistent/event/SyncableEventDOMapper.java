package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.event;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 14:59
 */
@Mapper
public interface SyncableEventDOMapper extends BaseMapper<SyncableEventDO> {
}
