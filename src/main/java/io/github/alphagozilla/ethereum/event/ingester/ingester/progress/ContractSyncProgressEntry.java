package io.github.alphagozilla.ethereum.event.ingester.ingester.progress;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.util.TimestampUtil;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 14:52
 */
@Component
public class ContractSyncProgressEntry {
    private final ContractSyncProgressRepository repository;

    public ContractSyncProgressEntry(ContractSyncProgressRepository repository) {
        this.repository = repository;
    }

    public ContractSyncProgress factoryNew(Address contract, BigInteger blockNum) {
        return ContractSyncProgress.builder()
                .contract(contract)
                .block(blockNum)
                .updatedAt(TimestampUtil.now())
                .build();
    }

    public Optional<ContractSyncProgress> load(Address contract) {
        ContractSyncProgress contractSyncProgress = repository.find(contract);
        return Optional.ofNullable(contractSyncProgress);
    }
}
