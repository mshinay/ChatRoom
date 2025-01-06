package com.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Utility {
    static Scanner scanner = new Scanner(System.in);

    /**
     *
     * @param length the length of string of input
     * @return the length-limited string
     */
    static public String GetString(int length) {
        String str = scanner.nextLine();
        return str.length() > length ? str.substring(0, length) : str;
    }

    static public String TimeFormat(LocalDateTime time) {
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return myFormatObj.format(time);
    }

    static public boolean CheckUser(String userId, String password) {
        if (userId == null || password == null) {//avoid null pointer
            return false;
        }
        Connection connection = null;
        String sql = "select * from users where UserID=? and Userpassword=?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection=JDBCUtils.getConnection();
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,userId);
            preparedStatement.setString(2,password);
            resultSet= preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtils.close(connection,preparedStatement,resultSet);
        }
    }
}
