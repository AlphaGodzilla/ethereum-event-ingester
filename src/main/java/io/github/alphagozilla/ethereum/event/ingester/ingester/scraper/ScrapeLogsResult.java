package io.github.alphagozilla.ethereum.event.ingester.ingester.scraper;

import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractRawEventLog;
import lombok.Builder;
import lombok.Value;

import java.math.BigInteger;
import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 16:45
 */
@Value
@Builder
public class ScrapeLogsResult {
    List<ContractRawEventLog> logs;

    BigInteger startBlock;

    BigInteger endBlock;

    public ScrapeLogsResult(List<ContractRawEventLog> logs, BigInteger startBlock, BigInteger endBlock) {
        this.logs = logs;
        this.startBlock = startBlock;
        this.endBlock = endBlock;
    }
}
