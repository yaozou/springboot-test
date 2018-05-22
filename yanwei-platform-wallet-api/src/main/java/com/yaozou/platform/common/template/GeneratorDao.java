package com.yanwei.platform.common.template;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 数据库操作类
 * @author luojh
 *
 */
public class GeneratorDao {

	public static List<Map<String, String>> list() {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		String sql = "select table_name  , engine, table_comment  , create_time   from information_schema.tables"
				+ " where table_schema = (select database())";
		try {
			List<Map<String, String>> result = jdbcUtil.findResult(sql, null);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			jdbcUtil.releaseConn();
		}
	}

	public static Map<String, String> get(String tableName) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		String sql = "select table_name  , engine, table_comment  , create_time   from information_schema.tables  "
				+ "			where table_schema = (select database()) and table_name = ?";
		try {
			// 创建填充参数的list
			List<Object> paramList = new ArrayList<Object>();
			// 填充参数
			paramList.add(tableName);
			List<Map<String, String>> result = jdbcUtil.findResult(sql,
					paramList);
			return result.get(0);
		} catch (SQLException e) {
			return null;
		} finally {
			jdbcUtil.releaseConn();
		}
	}

	public static List<Map<String, String>> listColumns(String tableName) {
		JdbcUtil jdbcUtil = new JdbcUtil();
		jdbcUtil.getConnection();
		String sql = "select column_name  , data_type  , column_comment  , column_key  , extra from information_schema.columns "
				+ " where table_name = ? and table_schema = (select database()) order by ordinal_position";
		try {
			// 创建填充参数的list
			List<Object> paramList = new ArrayList<Object>();
			// 填充参数
			paramList.add(tableName);
			List<Map<String, String>> result = jdbcUtil.findResult(sql,paramList);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			jdbcUtil.releaseConn();
		}
	}
}
