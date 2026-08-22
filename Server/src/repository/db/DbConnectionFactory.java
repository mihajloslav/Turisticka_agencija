/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository.db;

import configuration.Configuration;
import constant.MyServerConstants;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author mihajlo
 */
public class DbConnectionFactory {

    private Connection connection;
    private static DbConnectionFactory instance;

    private DbConnectionFactory() {
    }

    public static DbConnectionFactory getInstance() {
        if (instance == null) {
            instance = new DbConnectionFactory();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException, IOException {
        if (connection == null || connection.isClosed()) {
            String url = Configuration.getInstance().getDbProperty(MyServerConstants.DB_CONFIG_URL);
            String user = Configuration.getInstance().getDbProperty(MyServerConstants.DB_CONFIG_USERNAME);
            String password = Configuration.getInstance().getDbProperty(MyServerConstants.DB_CONFIG_PASSWORD);

            connection = DriverManager.getConnection(url, user, password);
            connection.setAutoCommit(false);
        }
        return connection;
    }
}
