package io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.math.BigInteger;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 11:39
 */
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class SyncableContract {
    /**
     * 合约地址
     */
    private final Address id;
    /**
     * 合约名
     */
    private final String name;
    /**
     * 开始区块
     */
    private final BigInteger initBlock;
    /**
     * 是否可同步
     */
    private Boolean syncable;

    public void syncable(boolean syncable) {
        this.syncable = syncable;
    }
}
