package io.github.alphagozilla.ethereum.event.ingester.ingester.contract;

import io.github.alphagozilla.ethereum.event.ingester.ingester.util.StringUtil;
import lombok.Getter;
import lombok.Value;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 16:17
 */
@Value
public class Address {
    @Getter
    String value;

    public Address(String value) {
        if (StringUtil.isEmpty(value)) {
            throw new IllegalArgumentException("invalid address value");
        }
        if (value.length() > 50) {
            throw new IllegalArgumentException("address too long");
        }
        this.value = value.toLowerCase();
    }
}
