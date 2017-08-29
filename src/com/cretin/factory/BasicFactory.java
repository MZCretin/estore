package com.cretin.factory;


import com.cretin.annotation.Tran;
import com.cretin.dao.Dao;
import com.cretin.service.Service;
import com.cretin.util.TransactionManager;

import java.io.FileReader;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Properties;

public class BasicFactory {
    private static BasicFactory factory = new BasicFactory();
    private static Properties prop = null;

    private BasicFactory() {

    }

    static {
        try {
            prop = new Properties();
            prop.load(new FileReader(BasicFactory.class.getClassLoader().
                    getResource("config.properties").getPath()));
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static BasicFactory getFactory() {
        return factory;
    }

    public <T extends Dao> T getDao(Class<T> clazz) {
        try {
            String infName = clazz.getSimpleName();
            String implNmae = prop.getProperty(infName);
            return ( T ) Class.forName(implNmae).newInstance();
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public <T extends Service> T getService(Class<T> clazz) {
        try {
            String infName = clazz.getSimpleName();
            String implNmae = prop.getProperty(infName);
            T t = ( T ) Class.forName(implNmae).newInstance();
            T proxy = ( T ) Proxy.newProxyInstance(t.getClass().getClassLoader(), t.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if ( method.isAnnotationPresent(Tran.class) ) {
                        //如果被Tran注解标记 则需要管理事物
                        try {
                            TransactionManager.startTran();
                            Object obj = method.invoke(t, args);
                            TransactionManager.commit();
                            return obj;
                        } catch ( InvocationTargetException e ) {
                            TransactionManager.rollback();
                            throw new RuntimeException(e.getTargetException());
                        } catch ( Exception e ) {
                            TransactionManager.rollback();
                            throw new RuntimeException(e);
                        } finally {
                            TransactionManager.release();
                        }
                    } else {
                        //如果没有被Tran注解标记 不用管理事务
                        return method.invoke(t, args);
                    }
                }
            });
            return proxy;
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
