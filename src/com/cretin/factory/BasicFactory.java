package com.cretin.factory;


import java.io.FileReader;
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

    public <T> T getInstance(Class<T> clazz) {
        try {
            String infName = clazz.getSimpleName();
            String implNmae = prop.getProperty(infName);
            return ( T ) Class.forName(implNmae).newInstance();
        } catch ( Exception e ) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
