package io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.contract;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import lombok.Builder;
import lombok.Value;

import java.sql.Timestamp;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 11:31
 */
@Value
@Builder
public class RegisterSyncableContract {
    Address address;

    String name;

    String initBlock;

    Boolean enable;

    Timestamp lastRegisterAt;
}
