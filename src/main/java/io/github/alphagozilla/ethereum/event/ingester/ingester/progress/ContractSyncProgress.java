package io.github.alphagozilla.ethereum.event.ingester.ingester.progress;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 14:51
 */
@Builder
@Getter
public class ContractSyncProgress {
    private Address contract;

    private BigInteger block;

    private Timestamp updatedAt;
}
