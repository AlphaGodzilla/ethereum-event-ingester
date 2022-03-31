package io.github.alphagozilla.ethereum.event.ingester.manage.domain.aggregation;

import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEvent;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventLogConsumer;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractRawEventLog;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract.SyncableContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.event.SyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeChannel;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticePayload;
import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 15:59
 */
@Builder
@Value
public class SyncableTask implements ContractEventLogConsumer {
    SyncableContract contract;
    List<SyncableEvent> events;
    List<NoticeChannel> channels;

    @Override
    public void consume(ContractRawEventLog rawLog, ContractEvent event, String eventJson) {
        for (final NoticeChannel channel : channels) {
            NoticePayload payload = NoticePayload.builder()
                    .raw(rawLog)
                    .eventName(event.getName())
                    .event(eventJson)
                    .build();
            channel.notice(payload);
        }
    }
}
