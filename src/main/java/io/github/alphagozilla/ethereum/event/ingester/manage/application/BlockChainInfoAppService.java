package io.github.alphagozilla.ethereum.event.ingester.manage.application;

import io.github.alphagozilla.ethereum.event.ingester.ingester.BlockChainInfoQuery;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * @author AlphaGodzilla
 * @date 2022/3/30 16:11
 */
@Service
public class BlockChainInfoAppService {
    private final BlockChainInfoQuery blockChainInfoQuery;

    public BlockChainInfoAppService(BlockChainInfoQuery blockChainInfoQuery) {
        this.blockChainInfoQuery = blockChainInfoQuery;
    }

    public BigInteger blockHeight() {
        return blockChainInfoQuery.blockHeight();
    }

    public BigInteger chainId() {
        return blockChainInfoQuery.chainId();
    }
}
