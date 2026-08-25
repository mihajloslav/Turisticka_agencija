/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuration;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author mihajlo
 */
public class Configuration {

    private static Configuration instance;
    private Properties dbConfig;
    private Properties serverConfig;

    private Configuration() {
        try {
            dbConfig = new Properties();
            try (FileInputStream in = new FileInputStream("config/dbconfig.properties")) {
                dbConfig.load(in);
            }
            serverConfig = new Properties();
            try (FileInputStream in = new FileInputStream("config/server.properties")) {
                serverConfig.load(in);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    public String getDbProperty(String key) {
        return dbConfig.getProperty(key, "н/д");
    }

    public void setDbProperty(String key, String value) {
        dbConfig.setProperty(key, value);
    }

    public String getServerProperty(String key) {
        return serverConfig.getProperty(key, "н/д");
    }

    public void setServerProperty(String key, String value) {
        serverConfig.setProperty(key, value);
    }

    public void sacuvajIzmene() throws IOException {
        try (FileOutputStream out = new FileOutputStream("config/dbconfig.properties")) {
            dbConfig.store(out, null);
        }
        try (FileOutputStream out = new FileOutputStream("config/server.properties")) {
            serverConfig.store(out, null);
        }
    }
}
