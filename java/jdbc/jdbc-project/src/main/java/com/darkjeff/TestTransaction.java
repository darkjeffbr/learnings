package com.darkjeff;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestTransaction {

    public static void main(String[] args) throws SQLException {

        Connection connection = ConnectionFactory.getConnection();
        connection.setAutoCommit(false);

        List<Integer> keys = new ArrayList<>();

        try{
            Statement statement = connection.createStatement();
            statement.execute("INSERT INTO PETS(NAME, TYPE) VALUES('AnimalT1', 'T')", Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            keys.add(rs.getInt(1));

            statement.execute("INSERT INTO PETS(NAME, TYPE) VALUES('AnimalT2', 'T')", Statement.RETURN_GENERATED_KEYS);
            rs = statement.getGeneratedKeys();
            rs.next();
            keys.add(rs.getInt(1));

            double random = Math.random();
            if(random > 0.5){
                throw new Exception();
            }
            System.out.println("Nice! Let's commit");
            connection.commit();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Yeah looks like something went wrong. Rolling back.");
            connection.rollback();
            keys.clear();
        }

        if(!keys.isEmpty()){
            System.out.println("Great! Let's after a successful commit lets see what was inserted");

            Statement statement = connection.createStatement();

            String sql = "SELECT ID, NAME, TYPE FROM PETS WHERE ID IN (" + keys.stream().map( k -> k + "").collect(Collectors.joining(",")) + ")";

            ResultSet rs = statement.executeQuery(sql);

            while(rs.next()){
                System.out.print(rs.getInt("ID") + " - ");
                System.out.print(rs.getString("NAME") + " - ");
                System.out.println(rs.getString("TYPE"));
            }

        }

        connection.close();
    }

}
