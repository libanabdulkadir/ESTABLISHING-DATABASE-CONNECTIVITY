package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/STMS";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertStudent(String regNo, String fname, String lname, String bday) throws SQLException {
        String query = "INSERT INTO students (reg_no, first_name, last_name, birthday) VALUES (?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, regNo);
            pstmt.setString(2, fname);
            pstmt.setString(3, lname);
            pstmt.setString(4, bday);
            pstmt.executeUpdate();
        }
    }
}
