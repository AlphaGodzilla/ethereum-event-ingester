package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 11:01
 */
@Mapper
public interface SyncableEventDOMapper extends BaseMapper<SyncableEventDO> {
}
