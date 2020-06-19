package com.darkjeff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestInsertPets {

    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();

        Statement statement = connection.createStatement();

        statement.execute("INSERT INTO PETS(NAME, TYPE) VALUES('Rex', 'Dog')", Statement.RETURN_GENERATED_KEYS);
        ResultSet resultSet = statement.getGeneratedKeys();
        resultSet.next();
        System.out.println("New register ID: " + resultSet.getInt(1));
        resultSet.close();

        statement.execute("INSERT INTO PETS(NAME, TYPE) VALUES('Thunder', 'Cat')", Statement.RETURN_GENERATED_KEYS);
        resultSet = statement.getGeneratedKeys();
        resultSet.next();
        System.out.println("New register ID: " + resultSet.getInt(1));
        resultSet.close();

        statement.execute("INSERT INTO PETS(NAME, TYPE) VALUES('Fernando', 'Duck')", Statement.RETURN_GENERATED_KEYS);
        resultSet = statement.getGeneratedKeys();
        resultSet.next();
        System.out.println("New register ID: " + resultSet.getInt(1));
        resultSet.close();

        statement.execute("INSERT INTO PETS(NAME, TYPE) VALUES('Lola', 'Cow')", Statement.RETURN_GENERATED_KEYS);
        resultSet = statement.getGeneratedKeys();
        resultSet.next();
        System.out.println("New register ID: " + resultSet.getInt(1));
        resultSet.close();

        statement.close();
        connection.close();
    }

}
