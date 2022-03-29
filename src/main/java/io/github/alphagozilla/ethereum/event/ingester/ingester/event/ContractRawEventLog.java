package io.github.alphagozilla.ethereum.event.ingester.ingester.event;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;
import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 16:15
 */
@Value
@Builder
public class ContractRawEventLog {
    boolean removed;
    String logIndex;
    String transactionIndex;
    String transactionHash;
    String blockHash;
    BigInteger blockNumber;
    Address address;
    String data;
    String type;
    List<String> topics;
}
