package io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventLogConsumer;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractRawEventLog;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.*;
import lombok.Builder;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 11:27
 */
@Slf4j
@Value
@Builder
public class RegisterNoticeChannel implements ContractEventLogConsumer {
    Address contract;

    NoticeChannelType noticeChannelType;

    String channel;

    NoticePostmanDispatcher noticePostmanDispatcher;

    @Override
    public void consume(ContractRawEventLog eventLog, String eventJson) {
        Optional<NoticePostman> noticePostmanOptional = noticePostmanDispatcher.dispatchPostman(getNoticeChannelType());
        if (noticePostmanOptional.isEmpty()) {
            return;
        }
        NoticePostman noticePostman = noticePostmanOptional.get();
        NoticeEnvelope envelope = NoticeEnvelope.builder()
                .noticeChannelType(getNoticeChannelType())
                .channel(getChannel())
                .build();
        NoticePayload payload = NoticePayload.builder()
                .raw(eventLog)
                .event(eventJson)
                .build();
        try {
            noticePostman.postNotice(envelope, payload);
        }catch (Exception exception) {
            log.error("事件通知异常", exception);
        }
    }
}
