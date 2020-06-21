package com.darkjeff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TestRemove {

    public static void main(String[] args) throws SQLException {
        Connection connection = ConnectionFactory.getConnection();

        connection.createStatement().execute("INSERT INTO Pets(name, type) VALUES ('Dragon', 'Rabbit')");

        PreparedStatement statement = connection.prepareStatement("DELETE FROM Pets WHERE name = ?");
        statement.setString(1, "Dragon");

        int affectedRows = statement.executeUpdate();

        System.out.println("(Should be 1) Deleted rows " + affectedRows);

        connection.close();


    }

}
