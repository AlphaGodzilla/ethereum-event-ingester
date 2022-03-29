package io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 11:33
 */
public interface RegisterSyncableContractRepository {
    /**
     * 先删除再保存
     * @param registerSyncableContract 合约
     */
    void deleteAndSave(RegisterSyncableContract registerSyncableContract);

    /**
     * 通过合约地址查找
     * @param address 合约地址
     * @return 注册的合约
     */
    RegisterSyncableContract findOfId(Address address);
}
