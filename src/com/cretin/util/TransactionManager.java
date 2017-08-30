package com.cretin.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import org.apache.commons.dbutils.DbUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

public class TransactionManager {
    private static ThreadLocal<Connection> conn_local = new ThreadLocal<Connection>();
    private static ThreadLocal<Connection> real_conn_local = new ThreadLocal<Connection>();

    //私有化构造方法
    private TransactionManager() {
    }

    //保证唯一的source
    private static DataSource source = new ComboPooledDataSource();

    private static ThreadLocal<Boolean> flag_local = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    /**
     * 开始事务
     */
    public static void startTran() throws SQLException {
        //记录开启了事务
        flag_local.set(true);
        Connection conn = source.getConnection();
        conn.setAutoCommit(false);
        real_conn_local.set(conn);

        Connection connectionProxy = ( Connection ) Proxy.newProxyInstance(conn.getClass().getClassLoader(), conn.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if ( "close".equals(method.getName()) ) {
                    //如果调用了close方法
                    return null;
                } else {
                    return method.invoke(conn, args);
                }
            }
        });
        conn_local.set(connectionProxy);
    }

    /**
     * 释放资源
     */
    public static void release() {
        DbUtils.closeQuietly(real_conn_local.get());
        real_conn_local.remove();
        conn_local.remove();
        flag_local.remove();
    }

    public static DataSource getSource() throws SQLException {
        if ( flag_local.get() ) {
            //开启了事务
            return ( DataSource ) Proxy.newProxyInstance(source.getClass().getClassLoader(), source.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ( "getConnection".equals(method.getName()) ) {
                        //获取连接的方法
                        return conn_local.get();
                    } else {
                        return method.invoke(source, args);
                    }
                }
            });
        } else {
            return source;
        }
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

}