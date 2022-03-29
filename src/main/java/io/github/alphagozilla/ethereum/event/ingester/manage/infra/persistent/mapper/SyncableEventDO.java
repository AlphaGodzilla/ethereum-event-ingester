package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventAbi;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.typehandler.AddressTypeHandler;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.typehandler.ContractEventAbiTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 11:02
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "syncable_event", autoResultMap = true)
public class SyncableEventDO {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * indexed
     */
    @TableField(typeHandler = AddressTypeHandler.class)
    private Address contract;

    private String eventName;

    @TableField(typeHandler = ContractEventAbiTypeHandler.class)
    private ContractEventAbi abi;
}
