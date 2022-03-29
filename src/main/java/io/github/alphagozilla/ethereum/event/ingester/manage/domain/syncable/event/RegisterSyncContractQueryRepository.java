package io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.RegisterContract;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 14:01
 */
public interface RegisterSyncContractQueryRepository {
    /**
     * 查询注册合约列表
     * @param enable 合约是否可用
     * @param contractAddress 合约地址
     * @return 注册合约列表
     */
    List<RegisterContract> list(Boolean enable, Address contractAddress);
}
