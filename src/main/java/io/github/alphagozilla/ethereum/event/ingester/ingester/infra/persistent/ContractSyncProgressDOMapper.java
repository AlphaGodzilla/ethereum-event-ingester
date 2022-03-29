package io.github.alphagozilla.ethereum.event.ingester.ingester.infra.persistent;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 15:04
 */
@Mapper
public interface ContractSyncProgressDOMapper extends BaseMapper<ContractSyncProgressDO> {
}
