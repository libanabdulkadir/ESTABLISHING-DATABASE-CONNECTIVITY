package org.example;
import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UI {
    JFrame frame;
    JTextField regNoField;
    JTextField fnameField;
    JTextField lnameField;
    JDateChooser bdayChooser;
    JLabel statusLabel;



    public UI() {
        frame = new JFrame("Student Registration");
        frame.setSize(400, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initializeFrame();
    }

    private void initializeFrame() {

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setPreferredSize(new Dimension(350, 600));

        panel.add(new JLabel("Registration No:"));
        regNoField = new JTextField();
        regNoField.setPreferredSize(new Dimension(150, 25));
        panel.add(regNoField);

        panel.add(new JLabel("First Name:"));
        fnameField = new JTextField();
        fnameField.setPreferredSize(new Dimension(150, 25));
        panel.add(fnameField);

        panel.add(new JLabel("Last Name:"));
        lnameField = new JTextField();
        lnameField.setPreferredSize(new Dimension(150, 25));
        panel.add(lnameField);

        panel.add(new JLabel("Birthday:"));
        bdayChooser = new JDateChooser();
        bdayChooser.setDateFormatString("yyyy-MM-dd");
        bdayChooser.setPreferredSize(new Dimension(150, 25));
        panel.add(bdayChooser);

        JButton submitButton = new JButton("Submit");
        panel.add(submitButton);

        statusLabel = new JLabel("Status: Waiting for input");
        panel.add(statusLabel);

        frame.setLayout(new GridBagLayout());
        frame.add(panel);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveToDatabase();
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void saveToDatabase() {
        String regNo = regNoField.getText();
        String fname = fnameField.getText();
        String lname = lnameField.getText();
        Date selectedDate = bdayChooser.getDate();
        String bday = new SimpleDateFormat("yyyy-MM-dd").format(selectedDate);

        try {
            DatabaseConnection.insertStudent(regNo, fname, lname, bday);
            statusLabel.setText("Status: Record Inserted");
            JOptionPane.showMessageDialog(frame, "Student Registered Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            statusLabel.setText("Status: Insert Failed");
            JOptionPane.showMessageDialog(frame, "Insert Failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
