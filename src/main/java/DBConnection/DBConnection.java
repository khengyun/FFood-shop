package DBConnection;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBConnection {
    private static Connection conn = null;

public static Connection getConnection() {
    if (conn != null) {
        return conn;
    }

    try {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream configFile = classLoader.getResourceAsStream("database.properties");
        if (configFile == null) {
            throw new RuntimeException("The database.properties file does not exist in the resources folder.");
        }

        Properties prop = new Properties();
        prop.load(configFile);

        String DATABASE_NAME = prop.getProperty("DATABASE_NAME");
        String PASSWORD = prop.getProperty("PASSWORD");
        String USERNAME = prop.getProperty("USERNAME");
        String DATA_SERVER_NAME = prop.getProperty("DATA_SERVER_NAME");

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String path = "jdbc:sqlserver://" + DATA_SERVER_NAME + "\\SQLEXPRESS:1433;"
                + "databaseName=" + DATABASE_NAME + ";"
                + "user=" + USERNAME + ";"
                + "password=" + PASSWORD + ";"
                + "encrypt=true;"
                + "trustServerCertificate=true;";
        conn = DriverManager.getConnection(path);
    } catch (Exception ex) {
        Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
    }
    return conn;
}


    public void close() {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}