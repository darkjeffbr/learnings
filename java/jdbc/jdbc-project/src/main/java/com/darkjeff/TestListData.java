package com.darkjeff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestListData {

    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();

        Statement statement = connection.createStatement();
        statement.execute("SELECT ID, NAME, TYPE FROM PETS");
        ResultSet resultSet = statement.getResultSet();

        while(resultSet.next()){
            System.out.print(resultSet.getInt("ID") + " - ");
            System.out.print(resultSet.getString("NAME") + " - ");
            System.out.println(resultSet.getString("TYPE"));
        }

        resultSet.close();
        statement.close();

        connection.close();
    }

}
