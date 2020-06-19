package com.darkjeff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.darkjeff.Constants.CONNECTION_URL;

public class ConnectionFactory {
    public static Connection getConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(CONNECTION_URL);
        }catch (SQLException e){
            throw new RuntimeException("Error: ", e);
        }
        return connection;
    }

}
