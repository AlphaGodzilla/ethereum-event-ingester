package io.github.alphagozilla.ethereum.event.ingester.manage.domain;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.NoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.SyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.SyncableEvent;
import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 19:24
 */
@Getter
@Builder
public class RegisterContract {
    private final Address address;

    private final String name;

    private final BigInteger initBlock;

    private final Boolean enable;

    private final List<SyncableEvent> events;

    private final Timestamp lastRegisterAt;

    private final NoticeChannel noticeChannel;

    public SyncableContract toSyncableContract() {
        return SyncableContract.builder()
                .address(getAddress())
                .name(getName())
                .initBlock(getInitBlock().toString())
                .enable(getEnable())
                .lastRegisterAt(getLastRegisterAt())
                .build();
    }
}
