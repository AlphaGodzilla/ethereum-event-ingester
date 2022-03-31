package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.contract.SyncableContract;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 13:41
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "syncable_contract", autoResultMap = true)
public class SyncableContractDO {
    /**
     * 合约地址
     */
    @TableId(type = IdType.INPUT)
    Address id;
    /**
     * 合约名
     */
    @TableField("`name`")
    String name;
    /**
     * 开始区块
     */
    String initBlock;
    /**
     * 是否可同步
     */
    Boolean syncable;

    public SyncableContractDO(SyncableContract syncableContract) {
        this.id = syncableContract.getId();
        this.name = syncableContract.getName();
        this.initBlock = syncableContract.getInitBlock().toString();
        this.syncable = syncableContract.getSyncable();
    }

    public SyncableContract toDomain() {
        return SyncableContract.builder()
                .id(getId())
                .name(getName())
                .initBlock(new BigInteger(getInitBlock()))
                .syncable(getSyncable())
                .build();
    }
}
