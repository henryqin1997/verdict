package edu.umich.mysql.jdbc;

import java.sql.DriverManager;

import java.sql.ResultSet;

import java.sql.SQLException;

import java.sql.Connection;

import java.sql.Statement;

import java.sql.PreparedStatement;

import java.lang.String;

import java.util.Scanner;

public  class mysql_driver{

    private static String username = "USERNAME_HERE";                    // place your uniqname here
    private static String password = "PASSWORD_HERE";                    // place your mysql password here

    static {
        try {

            Connection conn = null;

            Scanner input_reader = new Scanner(System.in);

            String url = "jdbc:mysql://localhost:3306/test1?useUnicode=true&charsetEncoding=utf8";

            Class.forName("com.mysql.jdbc.Driver");

            System.out.println("Successfully load MySQL driver");

            conn = DriverManager.getConnection(url, username, password);

            Statement stmt = conn.createStatement();

            String sql;

            String Feedback;

            PreparedStatement usersInput = null;

            while (true) {

                System.out.println("Please input a query, or enter 'quit' to quit ");

                sql = input_reader.next();

                if (sql == "quit") {

                    break;

                }

                //further implementation of queries should be written as class or function, below is only very ugly implmentation

                else {

                    if (sql.toLowerCase().indexOf("select") == 0) {

                        usersInput = conn.prepareStatement(sql);

                        ResultSet result = usersInput.executeQuery();

                        //what to print depends on tables in sql, so not shown here

                    } else {

                        usersInput = conn.prepareStatement(sql);

                        int result = usersInput.executeUpdate();

                        Feedback = result >= 1 ? "success" : "failure";

                        System.out.println(Feedback);

                    }

                }
            }

            conn.close();

        } catch (SQLException e) {

            System.out.println("MySQL incorrect action");

            e.printStackTrace();

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}