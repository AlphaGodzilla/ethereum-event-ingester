package io.github.alphagozilla.ethereum.event.ingester.notice.domain;

import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractRawEventLog;
import lombok.Builder;
import lombok.Value;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 13:52
 */
@Value
@Builder
public class NoticePayload {
    ContractRawEventLog raw;
    String event;
}
