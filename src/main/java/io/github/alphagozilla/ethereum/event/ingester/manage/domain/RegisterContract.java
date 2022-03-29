package io.github.alphagozilla.ethereum.event.ingester.manage.domain;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.RegisterNoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract.RegisterSyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.RegisterSyncableEvent;
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

    private final List<RegisterSyncableEvent> events;

    private final Timestamp lastRegisterAt;

    private final RegisterNoticeChannel registerNoticeChannel;

    public RegisterSyncableContract toSyncableContract() {
        return RegisterSyncableContract.builder()
                .address(getAddress())
                .name(getName())
                .initBlock(getInitBlock().toString())
                .enable(getEnable())
                .lastRegisterAt(getLastRegisterAt())
                .build();
    }
}
