package Screens;

import Config.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceBackup extends JPanel {

    private JLabel messageLabel;
    public AttendanceBackup(CardLayout cardLayout, JPanel mainContainer) {
        setLayout(null);
        setBackground(new Color(34, 177, 76));

        // Calculate center of the panel
        int centerX = 800 / 2;  // Panel width is 800

        // Back button (top left corner)
        JButton backButton = new JButton("Back");
        backButton.setBounds(20, 20, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        add(backButton);

        // Title
        JLabel titleLabel = new JLabel("Attendance Backup", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 80, 800, 30);
        add(titleLabel);

        // Date
        JLabel dateLabel = new JLabel(new SimpleDateFormat("MMMM d, yyyy").format(new Date()), SwingConstants.CENTER);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 18));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setBounds(0, 130, 800, 25);
        add(dateLabel);

        // Time
        JLabel timeLabel = new JLabel(new SimpleDateFormat("h:mm a").format(new Date()), SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBounds(0, 160, 800, 25);
        add(timeLabel);

        // ID Label and Field
        JLabel idLabel = new JLabel("Employee ID", SwingConstants.CENTER);
        idLabel.setFont(new Font("Arial", Font.BOLD, 16));
        idLabel.setForeground(Color.WHITE);
        idLabel.setBounds(0, 220, 800, 25);
        add(idLabel);

        JTextField idField = new JTextField();
        idField.setFont(new Font("Arial", Font.PLAIN, 16));
        idField.setHorizontalAlignment(JTextField.CENTER);
        int fieldWidth = 250;
        idField.setBounds(centerX - (fieldWidth/2), 250, fieldWidth, 35);
        add(idField);

        // Submit Button
        JButton submitButton = new JButton("Submit");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        int buttonWidth = 150;
        submitButton.setBounds(centerX - (buttonWidth/2), 310, buttonWidth, 40);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        submitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(submitButton);

        // Message Label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 18));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setBounds(0, 380, 800, 30);
        add(messageLabel);

        submitButton.addActionListener(e -> {
            String empID = idField.getText().trim();
            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

            try {
                Connection conn = JDBC.getConnection();

                // Get employee name
                String nameSql = "SELECT first_name, last_name FROM employees WHERE employee_id = ?";
                PreparedStatement nameStmt = conn.prepareStatement(nameSql);
                nameStmt.setString(1, empID);
                ResultSet nameRs = nameStmt.executeQuery();

                if (nameRs.next()) {
                    String firstName = nameRs.getString("first_name");
                    String lastName = nameRs.getString("last_name");

                    // Check if timecard exists
                    String checkSql = "SELECT time_in, time_out FROM timecards WHERE employee_id = ? AND date = ?";
                    PreparedStatement checkStmt = conn.prepareStatement(checkSql);
                    checkStmt.setString(1, empID);
                    checkStmt.setString(2, currentDate);
                    ResultSet checkRs = checkStmt.executeQuery();

                    if (checkRs.next()) {
                        String timeOut = checkRs.getString("time_out");
                        if (timeOut == null) {
                            // Get time in value
                            Time timeIn = checkRs.getTime("time_in");
                            Time timeOutObj = Time.valueOf(currentTime);

                            // Calculate hours and minutes clocked
                            long diffMillis = timeOutObj.getTime() - timeIn.getTime();
                            double hoursClocked = diffMillis / (1000.0 * 60 * 60);
                            double minutesClocked = (diffMillis % (1000 * 60 * 60)) / (1000.0 * 60);

                            // Update with time out and clocked time
                            String updateSql = "UPDATE timecards SET time_out = ?, hours_clocked = ?, minutes_clocked = ? WHERE employee_id = ? AND date = ?";
                            PreparedStatement updateStmt = conn.prepareStatement(updateSql);
                            updateStmt.setString(1, currentTime);
                            updateStmt.setDouble(2, hoursClocked);
                            updateStmt.setDouble(3, minutesClocked);
                            updateStmt.setString(4, empID);
                            updateStmt.setString(5, currentDate);
                            updateStmt.executeUpdate();

                            messageLabel.setText("Goodbye, " + firstName + " " + lastName + "! Have a great day!");
                        } else {
                            messageLabel.setText("Timecard already exists!");
                        }
                    } else {
                        // Time in - create new record
                        String insertSql = "INSERT INTO timecards (employee_id, date, time_in) VALUES (?, ?, ?)";
                        PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                        insertStmt.setString(1, empID);
                        insertStmt.setString(2, currentDate);
                        insertStmt.setString(3, currentTime);
                        insertStmt.executeUpdate();

                        messageLabel.setText("Good day, " + firstName + " " + lastName + "! Time in recorded.");
                    }
                } else {
                    messageLabel.setText("Employee ID not found");
                }

                JDBC.closeResources(conn, null);

            } catch (SQLException ex) {
                ex.printStackTrace();
                messageLabel.setText("Database error!");
            }

            // Clear the input field
            idField.setText("");
        });

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        messageLabel.setForeground(Color.WHITE);
        messageLabel.setBounds(0, 250, 800, 30);
        add(messageLabel);

        backButton.addActionListener(e -> {
            // Go back to Attendance table view
            cardLayout.show(mainContainer, "table");
        });
    }
}