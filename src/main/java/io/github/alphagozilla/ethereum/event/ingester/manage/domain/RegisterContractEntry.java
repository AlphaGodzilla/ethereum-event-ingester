package io.github.alphagozilla.ethereum.event.ingester.manage.domain;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.util.TimestampUtil;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeChannelType;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.SyncableEvent;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 19:34
 */
@Component
public class RegisterContractEntry {
    private final RegisterContractRepository repository;

    public RegisterContractEntry(RegisterContractRepository repository) {
        this.repository = repository;
    }

    public RegisterContract factoryNew(Address contract,
                                       String name,
                                       BigInteger initBlock,
                                       List<SyncableEvent> events,
                                       NoticeChannelType channelType,
                                       String channelValue
    ) {
        io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.NoticeChannel noticeChannel = io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel.NoticeChannel.builder()
                .address(contract)
                .noticeChannelType(channelType)
                .channel(channelValue)
                .build();
        return RegisterContract.builder()
                .address(contract)
                .name(name)
                .initBlock(initBlock)
                .enable(true)
                .events(events)
                .lastRegisterAt(TimestampUtil.now())
                .noticeChannel(noticeChannel)
                .build();
    }

    public RegisterContract load(Address contract) {
        return repository.findOfId(contract);
    }
}
