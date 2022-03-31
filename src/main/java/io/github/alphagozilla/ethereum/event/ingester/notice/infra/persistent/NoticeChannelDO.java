package io.github.alphagozilla.ethereum.event.ingester.notice.infra.persistent;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.ChannelType;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeChannel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 11:01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("notice_channel")
public class NoticeChannelDO {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 渠道名
     */
    private String name;
    /**
     * 渠道类型
     */
    private ChannelType type;
    /**
     * 渠道值
     */
    private String value;

    public NoticeChannelDO(NoticeChannel noticeChannel) {
        this.id = noticeChannel.getId();
        this.name = noticeChannel.getName();
        this.type = noticeChannel.getType();
        this.value = noticeChannel.getValue();
    }
}
