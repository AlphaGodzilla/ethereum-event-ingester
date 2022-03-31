package io.github.alphagozilla.ethereum.event.ingester.manage.domain.bind;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import lombok.Builder;
import lombok.Value;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 15:16
 */
@Builder
@Value
public class ContractChannelBind {
    Address contract;

    Long channel;
}
