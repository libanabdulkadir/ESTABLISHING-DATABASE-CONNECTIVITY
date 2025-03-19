package org.example;

import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UIIntegrationTest {
    @Test
    void testUIInteractionWithDatabase() throws SQLException {
        // Step 1:
        UI ui = new UI();

        ui.regNoField.setText("23/1980/BIT-S");
        ui.fnameField.setText("Ali");
        ui.lnameField.setText("Muse");
        ui.bdayChooser.setDate(new java.util.Date());

        // Step 2:
        JPanel panel = (JPanel) ui.frame.getContentPane().getComponent(0);
        JButton submitButton = (JButton) panel.getComponent(8);
        submitButton.doClick();

        // Step 3:
        assertEquals("Status: Record Inserted", ui.statusLabel.getText(), "The status label should indicate success.");

        // Step 4:
        Connection conn = DatabaseConnection.connect();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM students WHERE reg_no = ?");
        pstmt.setString(1, "23/1980/BIT-S");
        ResultSet rs = pstmt.executeQuery();

        // Step 5:
        assertTrue(rs.next(), "Student should exist in the database.");
        assertEquals("Ali", rs.getString("first_name"), "First name should match.");
        assertEquals("Muse", rs.getString("last_name"), "Last name should match.");
        assertEquals("2025-03-19", rs.getDate("birthday").toString(), "Birthday should match.");

        // Step 6:
        conn.close();
    }
}
