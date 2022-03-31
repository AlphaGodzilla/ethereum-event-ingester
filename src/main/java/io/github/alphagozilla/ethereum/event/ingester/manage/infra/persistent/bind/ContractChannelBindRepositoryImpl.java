package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.bind;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.bind.ContractChannelBind;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.bind.ContractChannelBindRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/31 15:18
 */
@Component
public class ContractChannelBindRepositoryImpl implements ContractChannelBindRepository {
    private final ContractChannelBindDOMapper contractChannelBindDOMapper;

    public ContractChannelBindRepositoryImpl(ContractChannelBindDOMapper contractChannelBindDOMapper) {
        this.contractChannelBindDOMapper = contractChannelBindDOMapper;
    }

    @Override
    public boolean saveIfNotExist(Address contract, long channelId) {
        LambdaQueryWrapper<ContractChannelBindDO> queryWrapper = Wrappers.lambdaQuery(ContractChannelBindDO.class)
                .eq(ContractChannelBindDO::getContract, contract.getValue())
                .eq(ContractChannelBindDO::getChannel, channelId);
        Long count = contractChannelBindDOMapper.selectCount(queryWrapper);
        if (count != null && count > 0) {
            return false;
        }
        ContractChannelBindDO contractChannelBindDO = ContractChannelBindDO.builder()
                .contract(contract)
                .channel(channelId)
                .build();
        return contractChannelBindDOMapper.insert(contractChannelBindDO) > 0;
    }

    @Override
    public List<ContractChannelBind> listByContract(Address contract) {
        LambdaQueryWrapper<ContractChannelBindDO> queryWrapper = Wrappers.lambdaQuery(ContractChannelBindDO.class)
                .eq(ContractChannelBindDO::getContract, contract.getValue());
        List<ContractChannelBindDO> contractChannelBindDOS = contractChannelBindDOMapper.selectList(queryWrapper);
        return contractChannelBindDOS.stream().map(i -> ContractChannelBind.builder()
                .contract(i.getContract())
                .channel(i.getChannel())
                .build()
        ).collect(Collectors.toList());
    }

    @Override
    public List<ContractChannelBind> listByChannel(long channelId) {
        LambdaQueryWrapper<ContractChannelBindDO> queryWrapper = Wrappers.lambdaQuery(ContractChannelBindDO.class)
                .eq(ContractChannelBindDO::getChannel, channelId);
        List<ContractChannelBindDO> contractChannelBindDOS = contractChannelBindDOMapper.selectList(queryWrapper);
        return contractChannelBindDOS.stream().map(i -> ContractChannelBind.builder()
                .contract(i.getContract())
                .channel(i.getChannel())
                .build()
        ).collect(Collectors.toList());
    }
}
