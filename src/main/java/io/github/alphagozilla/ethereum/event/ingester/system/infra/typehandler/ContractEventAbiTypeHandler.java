package io.github.alphagozilla.ethereum.event.ingester.system.infra.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventAbi;
import io.github.alphagozilla.ethereum.event.ingester.ingester.util.StringUtil;
import io.github.alphagozilla.ethereum.event.ingester.system.infra.ApplicationContextHolder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 10:50
 */
public class ContractEventAbiTypeHandler extends BaseTypeHandler<ContractEventAbi> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, ContractEventAbi eventAbi, JdbcType jdbcType) throws SQLException {
        if (eventAbi == null) {
            return;
        }
        ContractEventAbiSerializable serializable = ContractEventAbiSerializable.builder()
                .name(eventAbi.getName())
                .inputs(eventAbi.getInputs().stream()
                        .map(input -> ContractEventAbiSerializable.Input.builder()
                                .indexed(input.isIndexed())
                                .name(input.getName())
                                .type(input.getType())
                                .build()
                        ).collect(Collectors.toList())
                ).build();
        ObjectMapper jackson = ApplicationContextHolder.getBean(ObjectMapper.class);
        try {
            preparedStatement.setString(i, jackson.writeValueAsString(serializable));
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    @Override
    public ContractEventAbi getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String abiStr = resultSet.getString(s);
        return parseToAbi(abiStr);
    }

    @Override
    public ContractEventAbi getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String abiStr = resultSet.getString(i);
        return parseToAbi(abiStr);
    }

    @Override
    public ContractEventAbi getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String abiStr = callableStatement.getString(i);
        return parseToAbi(abiStr);
    }

    private ContractEventAbi parseToAbi(String abiStr) {
        if (StringUtil.isEmpty(abiStr)) {
            return null;
        }
        ObjectMapper jackson = ApplicationContextHolder.getBean(ObjectMapper.class);
        try {
            ContractEventAbiSerializable contractEventAbiSerializable = jackson.readValue(
                    abiStr, ContractEventAbiSerializable.class
            );
            return ContractEventAbi.builder()
                    .name(contractEventAbiSerializable.getName())
                    .inputs(contractEventAbiSerializable.getInputs().stream()
                            .map(i -> ContractEventAbi.Input.builder()
                                    .indexed(i.isIndexed())
                                    .type(i.getType())
                                    .name(i.getName())
                                    .build()
                            ).collect(Collectors.toList())
                    ).build();
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ContractEventAbiSerializable {
        String name;
        List<Input> inputs;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Input {
            boolean indexed;
            String type;
            String name;
        }
    }
}
