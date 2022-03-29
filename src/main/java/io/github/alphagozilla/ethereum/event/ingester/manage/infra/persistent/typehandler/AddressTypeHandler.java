package io.github.alphagozilla.ethereum.event.ingester.manage.infra.persistent.typehandler;

import io.github.alphagozilla.ethereum.event.ingester.ingester.contract.Address;
import io.github.alphagozilla.ethereum.event.ingester.ingester.util.StringUtil;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author AlphaGodzilla
 * @date 2022/3/25 10:11
 */
public class AddressTypeHandler extends BaseTypeHandler<Address> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Address address, JdbcType jdbcType) throws SQLException {
        if (address == null) {
            return;
        }
        preparedStatement.setString(i, address.getValue());
    }

    @Override
    public Address getNullableResult(ResultSet resultSet, String s) throws SQLException {
        String address = resultSet.getString(s);
        if (StringUtil.isEmpty(address)) {
            return null;
        }
        return new Address(address);
    }

    @Override
    public Address getNullableResult(ResultSet resultSet, int i) throws SQLException {
        String address = resultSet.getString(i);
        if (StringUtil.isEmpty(address)) {
            return null;
        }
        return new Address(address);
    }

    @Override
    public Address getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        String address = callableStatement.getString(i);
        if (StringUtil.isEmpty(address)) {
            return null;
        }
        return new Address(address);
    }
}
