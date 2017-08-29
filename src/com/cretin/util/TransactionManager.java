package com.cretin.util;

import org.apache.commons.dbutils.DbUtils;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManager {
    private static ThreadLocal<Connection> conn_local = new ThreadLocal<Connection>() {
        @Override
        protected Connection initialValue() {
            return DaoUtils.getConn();
        }
    };

    //私有化构造方法
    private TransactionManager() {
    }

    /**
     * 开始事务
     */
    public static void startTran() throws SQLException {
        conn_local.get().setAutoCommit(false);
    }

    /**
     * 释放资源
     */
    public static void release() {

    }

    /**
     * 提交事务
     */
    public static void commit() {
        DbUtils.commitAndCloseQuietly(conn_local.get());
    }

    /**
     * 回滚事务
     */
    public static void rollback() {
        DbUtils.rollbackAndCloseQuietly(conn_local.get());
    }

    /**
     * 获取连接
     *
     * @return
     */
    public static Connection getConn() {
        return conn_local.get();
    }
}