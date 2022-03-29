package io.github.alphagozilla.ethereum.event.ingester.ingester.infra.web3j.impl;

import io.github.alphagozilla.ethereum.event.ingester.ingester.BlockChainHeightQuery;
import io.github.alphagozilla.ethereum.event.ingester.ingester.infra.web3j.client.Web3jFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 15:15
 */
@CacheConfig
@Component
public class Web3jBlockChainHeightQuery implements BlockChainHeightQuery {
    private final Web3jFactory web3jFactory;

    public Web3jBlockChainHeightQuery(Web3jFactory web3jFactory) {
        this.web3jFactory = web3jFactory;
    }

    @Override
    @Cacheable(value = "blockChainHeight", cacheManager = "blockHeightCacheManager")
    public BigInteger blockHeight() {
        try {
            return web3jFactory.getClient().ethBlockNumber().send().getBlockNumber();
        }catch (IOException exception) {
            throw new RuntimeException("block number retrieve error", exception);
        }
    }
}
