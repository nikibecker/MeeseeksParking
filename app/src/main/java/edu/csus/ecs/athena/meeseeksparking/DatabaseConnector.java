package edu.csus.ecs.athena.meeseeksparking;

import android.database.SQLException;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnector {

    // Connect to database
    private String host = "meeseeks-server.mysql.database.azure.com";
    private String database = "meeseeksdb";
    private String user = "Meeseeks@meeseeks-server";
    private String password = "SeniorProject18";
    private Connection connection = null;

    protected DatabaseConnector() {
        // check that the driver is installed
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Log.e("TAG", e.getMessage(), e);
            //throw new ClassNotFoundException("MySQL JDBC driver NOT detected in library path.", e);
        }

        // Initialize connection object
        try {
            String url = String.format("jdbc:mysql://%s/%s", host, database);

            // Set connection properties.
            Properties properties = new Properties();
            properties.setProperty("user", user);
            properties.setProperty("password", password);
            properties.setProperty("useSSL", "true");
            properties.setProperty("verifyServerCertificate", "true");
            properties.setProperty("requireSSL", "false");

            // get connection
            connection = DriverManager.getConnection(url, properties);
        } catch (java.sql.SQLException e) {
            throw new SQLException("Failed to create connection to database.", e);
        }
    }

    protected Connection getConnection() {
        return (connection != null) ? connection : null;
    }
}
