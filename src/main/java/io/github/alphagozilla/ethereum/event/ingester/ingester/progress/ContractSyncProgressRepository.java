package io.github.alphagozilla.ethereum.event.ingester.ingester.progress;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 14:52
 */
public interface ContractSyncProgressRepository {
    /**
     * 保存或更新同步进度
     * @param progress 进度
     * @return
     */
    boolean saveOrUpdate(ContractSyncProgress progress);

    /**
     * 查询进度
     * @param contract 合约地址
     * @return 进度对象
     */
    ContractSyncProgress find(Address contract);
}
