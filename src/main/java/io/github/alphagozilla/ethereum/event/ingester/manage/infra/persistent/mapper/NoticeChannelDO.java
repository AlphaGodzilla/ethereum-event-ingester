package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeChannelType;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.typehandler.AddressTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AlphaGodzilla
 * @date 2022/3/29 12:01
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "notice_channel", autoResultMap = true)
public class NoticeChannelDO {
    @TableId(type = IdType.INPUT)
    @TableField(typeHandler = AddressTypeHandler.class)
    private Address address;

    private NoticeChannelType noticeChannelType;

    private String channel;
}
