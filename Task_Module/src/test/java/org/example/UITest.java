
package org.example;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class UITest {

    @Test
    public void testInitializeFrame() {
        UI ui = new UI();

        assertNotNull(ui.frame);
        assertTrue(ui.frame.isVisible());

        assertNotNull(ui.regNoField);
        assertNotNull(ui.fnameField);
        assertNotNull(ui.lnameField);
        assertNotNull(ui.bdayChooser);
        assertNotNull(ui.statusLabel);
    }


    @Test
    void testSaveToDatabase() {
        UI ui = new UI();
        ui.regNoField.setText("23/1980/BIT-S");
        ui.fnameField.setText("Ali");
        ui.lnameField.setText("Muse");
        ui.bdayChooser.setDate(new java.util.Date());


        JPanel panel = (JPanel) ui.frame.getContentPane().getComponent(0);
        JButton submitButton = (JButton) panel.getComponent(8);
         submitButton.doClick();

        assertEquals("Status: Record Inserted", ui.statusLabel.getText());
    }


    @Test
    void testConnect() throws SQLException {
        Connection conn = DatabaseConnection.connect();
        assertNotNull(conn, "Connection should not be null");
        assertFalse(conn.isClosed(), "Connection should be open");
        conn.close();
    }


    @Test
    void testInsertStudent() throws SQLException {
        DatabaseConnection.insertStudent("23/1980/BIT-S", "Ali", "Muse", "2000-01-01");

        Connection conn = DatabaseConnection.connect();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM students WHERE reg_no = ?");
        pstmt.setString(1, "23/1980/BIT-S");
        ResultSet rs = pstmt.executeQuery();

        assertTrue(rs.next(), "Student should exist in the database");
        assertEquals("Ali", rs.getString("first_name"), "First name should match");
        assertEquals("Muse", rs.getString("last_name"), "Last name should match");
        assertEquals("2025-03-19", rs.getDate("birthday").toString(), "Birthday should match");

        conn.close();
    }



    }

