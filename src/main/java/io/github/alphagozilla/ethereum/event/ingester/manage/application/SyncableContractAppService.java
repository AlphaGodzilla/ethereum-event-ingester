package io.github.alphagozilla.ethereum.event.ingester.manage.application;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.FlowableEventContract;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.FlowableEventContractConfig;
import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.FlowableEventContractFactory;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventFactory;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.RegisterContract;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.RegisterContractEntry;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.RegisterContractRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.RegisterSyncContractQueryRepository;
import io.github.alphagozilla.ethereum.event.ingester.manage.domain.syncable.event.RegisterSyncableEvent;
import io.github.alphagozilla.ethereum.event.ingester.notice.domain.NoticeChannelType;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 13:35
 */
@Component
public class SyncableContractAppService {
    private final RegisterContractEntry registerContractEntry;
    private final RegisterContractRepository registerContractRepository;
    private final RegisterSyncContractQueryRepository registerSyncContractQueryRepository;
    private final FlowableEventContractFactory flowableEventContractFactory;
    private final ContractEventFactory contractEventFactory;

    public SyncableContractAppService(RegisterContractEntry registerContractEntry,
                                      RegisterContractRepository registerContractRepository,
                                      RegisterSyncContractQueryRepository registerSyncContractQueryRepository,
                                      FlowableEventContractFactory flowableEventContractFactory,
                                      ContractEventFactory contractEventFactory
    ) {
        this.registerContractEntry = registerContractEntry;
        this.registerContractRepository = registerContractRepository;
        this.registerSyncContractQueryRepository = registerSyncContractQueryRepository;
        this.flowableEventContractFactory = flowableEventContractFactory;
        this.contractEventFactory = contractEventFactory;
    }

    /**
     * 注册需要同步的合约
     *
     * @param contract  合约地址
     * @param name      合约名
     * @param initBlock 初始化区块号码
     * @param events    需要同步的合约事件
     */
    public void registerContract(Address contract,
                                 String name,
                                 BigInteger initBlock,
                                 List<RegisterSyncableEvent> events,
                                 NoticeChannelType noticeChannelType,
                                 String channelValue
    ) {
        RegisterContract registerContract = registerContractEntry.factoryNew(
                contract, name, initBlock, events, noticeChannelType, channelValue
        );
        registerContractRepository.coverSave(registerContract);
    }

    /**
     * 列出所有可同步的已注册合约
     *
     * @return 合约列表
     */
    public List<FlowableEventContract> listAllEnableContracts() {
        List<RegisterContract> registerContracts = registerSyncContractQueryRepository.list(true, null);
        return registerContracts.stream()
                .collect(Collectors.groupingBy(RegisterContract::getName, Collectors.toList()))
                .entrySet()
                .stream()
                .filter(i -> !i.getValue().isEmpty())
                .map(i -> FlowableEventContractConfig.builder()
                        .name(i.getKey())
                        .addresses(i.getValue().stream().map(RegisterContract::getAddress).collect(Collectors.toSet()))
                        .initBlock(i.getValue().stream().map(RegisterContract::getInitBlock)
                                .min(BigInteger::compareTo)
                                .orElse(BigInteger.ZERO)
                        )
                        .events(i.getValue().stream().flatMap(contract -> contract.getEvents().stream())
                                .map(e -> contractEventFactory.factoryNew(e.getEventName(), e.getAbi()))
                                .collect(Collectors.toSet())
                        )
                        .eventLogConsumer(i.getValue().stream()
                                .findAny().map(RegisterContract::getRegisterNoticeChannel).orElse(null)
                        ).build()
                )
                .map(flowableEventContractFactory::factoryNew)
                .collect(Collectors.toList());
    }
}
