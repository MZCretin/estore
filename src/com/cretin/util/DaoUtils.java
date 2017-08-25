package com.cretin.util;


import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class DaoUtils {
	private static DataSource source = new ComboPooledDataSource();
	private DaoUtils() {
	}
	
	public static DataSource getSource(){
		return source;
	}
	
	public static Connection getConn(){
		try {
			return source.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
