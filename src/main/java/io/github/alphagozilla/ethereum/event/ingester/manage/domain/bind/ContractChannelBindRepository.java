package io.github.alphagozilla.ethereum.event.ingester.manage.domain.bind;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;

import java.util.List;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 15:15
 */
public interface ContractChannelBindRepository {
    boolean saveIfNotExist(Address contract, long channelId);

    List<ContractChannelBind> listByContract(Address contract);

    List<ContractChannelBind> listByChannel(long channelId);
}
