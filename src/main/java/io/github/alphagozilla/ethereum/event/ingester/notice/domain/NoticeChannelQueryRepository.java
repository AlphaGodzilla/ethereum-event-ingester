package io.github.alphagozilla.ethereum.event.ingester.notice.domain;

import java.util.List;
import java.util.Set;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 11:52
 */
public interface NoticeChannelQueryRepository {
    /**
     * 查询渠道ID列表
     *
     * @param idSet id列表
     * @return 渠道列表
     */
    List<NoticeChannel> list(Set<Long> idSet);
}
