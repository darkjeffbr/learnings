package com.darkjeff;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestCreatePetsTable {

    public static void main(String[] args) throws SQLException {

        Connection connection = ConnectionFactory.getConnection();

        Statement statement = connection.createStatement();

        boolean select = statement.execute("CREATE TABLE IF NOT EXISTS Pets(ID SERIAL PRIMARY  KEY, NAME VARCHAR(100) NOT NULL, TYPE VARCHAR(50) NOT NULL )");

        if(select){
            System.out.println("SELECT WAS ISSUED");
        }else {
            System.out.println("STATEMENT DIFFERENT FROM SELECT WAS ISSUED");
        }

        select = statement.execute("SELECT * FROM Pets");

        if(select){
            System.out.println("SELECT WAS ISSUED");
        }else {
            System.out.println("STATEMENT DIFFERENT FROM SELECT WAS ISSUED");
        }

        statement.close();
        connection.close();

    }

}
