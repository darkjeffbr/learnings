package com.darkjeff;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static com.darkjeff.Constants.CONNECTION_URL;

public class TestInsertPets {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(CONNECTION_URL);

        Statement statement = connection.createStatement();
        statement.execute("INSERT INTO PETS(NAME, TYPE) VALUES('Rex', 'Dog')");
        statement.execute("INSERT INTO PETS(NAME, TYPE) VALUES('Thunder', 'Cat')");
        statement.execute("INSERT INTO PETS(NAME, TYPE) VALUES('Fernando', 'Duck')");
        statement.execute("INSERT INTO PETS(NAME, TYPE) VALUES('Lola', 'Cow')");

        connection.close();
    }

}
