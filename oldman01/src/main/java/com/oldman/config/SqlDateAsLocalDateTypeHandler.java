package com.oldman.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * 查询结果为 Map 时，MySQL DATE 列默认会被映射成 java.sql.Date，
 * JSON 序列化后变成按 UTC 换算的时间戳字符串（例如 2026-03-01 变成 2026-02-28T16:00:00.000Z），前端显示会差一天。
 * 这里把 DATE 列统一读成 LocalDate，序列化结果为 "2026-03-01"。
 */
public class SqlDateAsLocalDateTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setObject(i, parameter);
    }

    @Override
    public Object getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return rs.getObject(columnName, LocalDate.class);
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return rs.getObject(columnIndex, LocalDate.class);
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return cs.getObject(columnIndex, LocalDate.class);
    }
}
