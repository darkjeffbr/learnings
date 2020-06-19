package com.darkjeff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.darkjeff.Constants.CONNECTION_URL;

public class TestGetConnection {

    public static void main(String[] args) throws SQLException {
        //If no exception is thrown, then it worked!
        Connection connection = ConnectionFactory.getConnection();
        connection.close();
    }
}
