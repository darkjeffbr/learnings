package com.darkjeff;

import java.sql.Connection;
import java.sql.SQLException;

public class TestGeneratingALotOfConnectionsPool {

    public static void main(String[] args) throws SQLException {

        for (int i = 0; i<50000; i++){
            System.out.println("Connection " + (i+1));
            Connection connection = ConnectionFactory.getConnectionFromPool();
        }

    }

}
