package io.github.alphagozilla.ethereum.event.ingester.manage.domain;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;

/**
 * @author AlphaGodzilla
 * @date 2022/3/24 19:31
 */
public interface RegisterContractRepository {
    /**
     * 保存或更新合约
     * @param contract 合约
     */
    void coverSave(RegisterContract contract);

    /**
     * 通过合约地址查找
     * @param contractAddress 合约地址
     * @return 注册的合约
     */
    RegisterContract findOfId(Address contractAddress);
}
