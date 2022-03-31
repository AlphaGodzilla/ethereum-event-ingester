package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 13:44
 */
@Mapper
public interface SyncableContractDOMapper extends BaseMapper<SyncableContractDO> {
}
