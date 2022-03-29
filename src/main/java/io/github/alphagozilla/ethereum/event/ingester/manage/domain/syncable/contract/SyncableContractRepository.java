package io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 11:33
 */
public interface SyncableContractRepository {
    /**
     * 先删除再保存
     * @param syncableContract 合约
     */
    void deleteAndSave(SyncableContract syncableContract);

    /**
     * 通过合约地址查找
     * @param address 合约地址
     * @return 注册的合约
     */
    SyncableContract findOfId(Address address);
}
