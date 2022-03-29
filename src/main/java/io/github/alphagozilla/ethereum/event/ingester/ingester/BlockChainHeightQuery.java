package io.github.alphagozilla.ethereum.event.ingester.ingester;

import java.math.BigInteger;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 16:58
 */
public interface BlockChainHeightQuery {
    /**
     * 查询当前区块高度
     * @return 当前区块高度
     */
    BigInteger blockHeight();
}
