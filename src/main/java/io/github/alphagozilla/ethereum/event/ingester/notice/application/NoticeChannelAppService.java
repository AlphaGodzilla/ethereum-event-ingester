package io.github.alphagozilla.ethereum.event.ingester.notice.application;

import io.github.alphagozilla.ethereum.event.ingester.notice.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 11:21
 */
@Service
public class NoticeChannelAppService {
    private final NoticeChannelEntry noticeChannelEntry;
    private final NoticeChannelRepository noticeChannelRepository;
    private final NoticeChannelQueryRepository noticeChannelQueryRepository;

    public NoticeChannelAppService(NoticeChannelEntry noticeChannelEntry,
                                   NoticeChannelRepository noticeChannelRepository,
                                   NoticeChannelQueryRepository noticeChannelQueryRepository
    ) {
        this.noticeChannelEntry = noticeChannelEntry;
        this.noticeChannelRepository = noticeChannelRepository;
        this.noticeChannelQueryRepository = noticeChannelQueryRepository;
    }

    public Optional<NoticeChannel> load(long id) {
        return noticeChannelEntry.load(id);
    }

    public void remove(long id) {
        noticeChannelRepository.remove(id);
    }

    public void register(String name, ChannelType type, String value) {
        NoticeChannel noticeChannel = noticeChannelEntry.factoryNew(name, type, value);
        noticeChannelRepository.saveOrUpdate(noticeChannel);
    }

    public List<NoticeChannel> channels(Set<Long> idSet) {
        return noticeChannelQueryRepository.list(idSet);
    }
}
