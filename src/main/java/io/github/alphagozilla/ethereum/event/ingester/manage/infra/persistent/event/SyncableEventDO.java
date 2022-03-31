package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.event;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventAbi;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.event.SyncableEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 14:24
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("syncable_event")
public class SyncableEventDO {
    @TableId(type = IdType.ASSIGN_ID)
    Long id;

    Address contract;

    @TableField("`name`")
    String name;

    ContractEventAbi abi;

    public SyncableEventDO(SyncableEvent domain) {
        this.id = domain.getId();
        this.contract = domain.getContract();
        this.name = domain.getName();
        this.abi = domain.getAbi();
    }

    public SyncableEvent toDomain() {
        return SyncableEvent.builder()
                .id(getId())
                .contract(getContract())
                .name(getName())
                .abi(getAbi())
                .build();
    }
}
