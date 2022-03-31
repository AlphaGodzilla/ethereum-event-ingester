package io.github.alphagozilla.ethereum.event.ingester.notice.domain;

import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 11:08
 */
@Component
public class NoticeChannelEntry {
    private final NoticeChannelRepository noticeChannelRepository;

    public NoticeChannelEntry(NoticeChannelRepository noticeChannelRepository) {
        this.noticeChannelRepository = noticeChannelRepository;
    }

    public Optional<NoticeChannel> load(long id) {
        return Optional.ofNullable(noticeChannelRepository.findOfId(id));
    }

    public NoticeChannel factoryNew(String name, ChannelType type, String value) {
        return NoticeChannel.builder()
                .name(name)
                .type(type)
                .value(value)
                .build();
    }
}
