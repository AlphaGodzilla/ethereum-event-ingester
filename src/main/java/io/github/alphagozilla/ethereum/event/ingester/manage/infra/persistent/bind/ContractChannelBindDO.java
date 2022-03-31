package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.bind;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 14:07
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("contract_channel_bind")
public class ContractChannelBindDO {
    Address contract;

    @TableField("`channel`")
    Long channel;
}
