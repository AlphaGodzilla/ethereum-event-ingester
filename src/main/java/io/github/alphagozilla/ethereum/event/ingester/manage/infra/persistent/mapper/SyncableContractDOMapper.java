package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 10:18
 */
@Mapper
public interface SyncableContractDOMapper extends BaseMapper<SyncableContractDO> {
}
