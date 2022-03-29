package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.alphagozilla.ethereum.event.ingester.ingester.event.ContractEventAbi;
import io.github.alphagozilla.ethereum.event.ingester.ingester.util.StringUtil;
import io.github.alphagozilla.ethereum.event.ingester.system.infra.ApplicationContextHolder;
import io.github.alphagozilla.ethereum.event.ingester.manage.infra.converter.ContractEventAbiConverter;
import lombok.Data;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
        ObjectMapper jackson = ApplicationContextHolder.getBean(ObjectMapper.class);
        try {
            preparedStatement.setString(i, jackson.writeValueAsString(eventAbi));
        }catch (JsonProcessingException exception) {
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
            return ContractEventAbiConverter.INSTANCE.toContractEventAbi(contractEventAbiSerializable);
        }catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    @Data
    public static class ContractEventAbiSerializable {
        String name;
        List<ContractEventAbi.Input> inputs;

        @Data
        public static class Input {
            boolean indexed;
            String type;
            String name;
        }
    }
}
