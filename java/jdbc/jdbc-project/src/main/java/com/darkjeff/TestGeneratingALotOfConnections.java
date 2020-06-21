package com.darkjeff;

import java.sql.Connection;

public class TestGeneratingALotOfConnections {

    public static void main(String[] args) {

        for (int i = 0; i<50000; i++){
            System.out.println("Connection " + (i+1));
            Connection connection = ConnectionFactory.getConnection();

        }

    }

}
