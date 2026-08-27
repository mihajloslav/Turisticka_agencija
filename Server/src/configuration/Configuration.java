/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package configuration;

import constant.MyServerConstants;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author mihajlo
 */
public class Configuration {

    private static final String CONFIG_DIR = "config";
    private static final String DB_CONFIG_PATH = CONFIG_DIR + "/dbconfig.properties";
    private static final String SERVER_CONFIG_PATH = CONFIG_DIR + "/server.properties";

    private static Configuration instance;
    private Properties dbConfig;
    private Properties serverConfig;

    private Configuration() {
        loadFromDisk();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    /**
     * Explicitly reloads both .properties files from disk into the existing
     * Singleton, without creating a new instance. Should be called whenever
     * something needs to see the current state on disk (e.g. before opening
     * the server configuration form), since the Singleton otherwise keeps
     * the values it loaded when it was first created.
     */
    public void reloadConfiguration() {
        loadFromDisk();
    }

    /**
     * Loads both .properties files independently, so that the absence of one
     * file does not prevent the other from loading (and vice versa). If a
     * file does not exist or cannot be read, the corresponding Properties
     * object stays empty instead of being null.
     */
    private void loadFromDisk() {
        dbConfig = new Properties();
        try (FileInputStream in = new FileInputStream(DB_CONFIG_PATH)) {
            dbConfig.load(in);
        } catch (IOException ex) {
            // File does not exist or cannot be loaded - dbConfig stays empty.
        }
        serverConfig = new Properties();
        try (FileInputStream in = new FileInputStream(SERVER_CONFIG_PATH)) {
            serverConfig.load(in);
        } catch (IOException ex) {
            // File does not exist or cannot be loaded - serverConfig stays empty.
        }
    }

    public String getDbProperty(String key) {
        return dbConfig.getProperty(key);
    }

    public void setDbProperty(String key, String value) {
        dbConfig.setProperty(key, value);
    }

    public String getServerProperty(String key) {
        return serverConfig.getProperty(key);
    }

    public void setServerProperty(String key, String value) {
        serverConfig.setProperty(key, value);
    }

    public void saveChanges() throws IOException {
        File dir = new File(CONFIG_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        try (FileOutputStream out = new FileOutputStream(DB_CONFIG_PATH)) {
            dbConfig.store(out, null);
        }
        try (FileOutputStream out = new FileOutputStream(SERVER_CONFIG_PATH)) {
            serverConfig.store(out, null);
        }
        loadFromDisk();
    }

    /**
     * Checks whether the server configuration exists and contains all the
     * values required for the server to start:
     * - both .properties files must exist on disk,
     * - url and username (dbconfig.properties) must be non-empty,
     * - port (server.properties) must be non-empty, an integer in range 0-65535.
     * Password is NOT required.
     *
     * Always reloads the files from disk before checking, so the result
     * never depends on stale values cached in the Singleton (e.g. if the
     * file was manually edited, deleted, or recreated while the application
     * is running).
     */
    public boolean isConfigurationValid() {
        loadFromDisk();

        if (!new File(DB_CONFIG_PATH).exists() || !new File(SERVER_CONFIG_PATH).exists()) {
            return false;
        }

        String url = dbConfig.getProperty(MyServerConstants.DB_CONFIG_URL);
        String username = dbConfig.getProperty(MyServerConstants.DB_CONFIG_USERNAME);
        String portText = serverConfig.getProperty(MyServerConstants.SERVER_CONFIG_PORT);

        if (url == null || url.trim().isEmpty()
                || username == null || username.trim().isEmpty()
                || portText == null || portText.trim().isEmpty()) {
            return false;
        }

        try {
            int port = Integer.parseInt(portText.trim());
            if (port < 0 || port > 65535) {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }

        return true;
    }
}
