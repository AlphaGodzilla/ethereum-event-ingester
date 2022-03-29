package io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 13:33
 */
public interface RegisterNoticeChannelRepository {
    boolean saveOrUpdate(RegisterNoticeChannel registerNoticeChannel);

    RegisterNoticeChannel findOfId(Address address);
}
