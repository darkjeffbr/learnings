package com.darkjeff;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.darkjeff.Constants.CONNECTION_URL;

public class ConnectionFactory {

    private static DataSource dataSource;

    static {

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass( "org.postgresql.Driver" );
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        cpds.setJdbcUrl( CONNECTION_URL);
        cpds.setMinPoolSize(5);
        cpds.setAcquireIncrement(1);
        cpds.setMaxPoolSize(10);

        dataSource = cpds;

    }

    public static Connection getConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(CONNECTION_URL);
        }catch (SQLException e){
            throw new RuntimeException("Error: ", e);
        }
        return connection;
    }

    public static Connection getConnectionFromPool() throws SQLException {

        return dataSource.getConnection();

    }

}
