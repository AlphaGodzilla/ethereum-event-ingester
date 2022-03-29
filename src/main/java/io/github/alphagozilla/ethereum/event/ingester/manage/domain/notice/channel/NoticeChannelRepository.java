package io.github.alphagozilla.ethereum.event.ingester.manage.domain.notice.channel;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 13:33
 */
public interface NoticeChannelRepository {
    boolean saveOrUpdate(NoticeChannel noticeChannel);

    NoticeChannel findOfId(Address address);
}
