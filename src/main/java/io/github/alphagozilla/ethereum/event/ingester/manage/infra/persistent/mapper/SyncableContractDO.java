package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.mapper;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.typehandler.AddressTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 10:09
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "register_syncable_contract", autoResultMap = true)
public class SyncableContractDO {
    @TableId(type = IdType.INPUT)
    @TableField(typeHandler = AddressTypeHandler.class)
    private Address address;

    private String name;

    private String initBlock;

    /**
     * indexed
     */
    private Boolean enable;

    private Timestamp lastRegisterAt;
}
