package io.github.alphagozilla.ethereum.event.ingester.notice.domain;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 10:58
 */
public interface NoticeChannelRepository {
    NoticeChannel findOfId(long id);

    boolean saveOrUpdate(NoticeChannel noticeChannel);

    boolean remove(long id);
}
