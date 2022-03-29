package io.github.alphagozilla.ethereum.event.ingester.ingester.progress;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 16:52
 */
@Component
public class SyncProgressManager {
    private final ContractSyncProgressEntry entry;
    private final ContractSyncProgressRepository repository;

    public SyncProgressManager(ContractSyncProgressEntry entry, ContractSyncProgressRepository repository) {
        this.entry = entry;
        this.repository = repository;
    }

    /**
     * 同步合约进度
     * @param contract 合约地址
     * @param endBlockNumber 结束的区块号
     */
    public void sync(Address contract, BigInteger endBlockNumber) {
        ContractSyncProgress contractSyncProgress = entry.load(contract).orElseGet(() ->
                entry.factoryNew(contract, endBlockNumber)
        );
        repository.saveOrUpdate(contractSyncProgress);
    }

    /**
     * 查询进度合约进度
     * @param contract 合约地址
     * @return 进度
     */
    public Optional<BigInteger> getProgress(Address contract) {
        return entry.load(contract).map(ContractSyncProgress::getBlock);
    }
}
