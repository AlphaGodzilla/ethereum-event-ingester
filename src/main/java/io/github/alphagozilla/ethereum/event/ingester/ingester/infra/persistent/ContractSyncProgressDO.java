package io.github.alphagozilla.ethereum.event.ingester.ingester.infra.persistent;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.progress.ContractSyncProgress;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 15:02
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "contract_sync_progress", autoResultMap = true)
public class ContractSyncProgressDO {
    @TableId(type = IdType.INPUT)
    private Address contract;

    private String block;

    private Timestamp updatedAt;

    public ContractSyncProgress toDomain() {
        return ContractSyncProgress.builder()
                .contract(getContract())
                .block(new BigInteger(getBlock()))
                .updatedAt(getUpdatedAt())
                .build();
    }
}
