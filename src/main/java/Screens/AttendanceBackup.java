package Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceBackup extends JPanel {

    public AttendanceBackup(CardLayout cardLayout, JPanel mainContainer) {
        setLayout(null);
        setBackground(new Color(34, 177, 76));

        JLabel titleLabel = new JLabel("Attendance Backup", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(0, 30, 800, 30);
        add(titleLabel);

        JLabel dateLabel = new JLabel(new SimpleDateFormat("MMMM d, yyyy").format(new Date()), SwingConstants.CENTER);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dateLabel.setForeground(Color.WHITE);
        dateLabel.setBounds(0, 70, 800, 20);
        add(dateLabel);

        JLabel timeLabel = new JLabel(new SimpleDateFormat("h:mm a").format(new Date()), SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timeLabel.setForeground(Color.WHITE);
        timeLabel.setBounds(0, 100, 800, 20);
        add(timeLabel);

        JLabel idLabel = new JLabel("Employee ID");
        idLabel.setBounds(300, 150, 200, 20);
        idLabel.setForeground(Color.WHITE);
        add(idLabel);

        JTextField idField = new JTextField();
        idField.setBounds(280, 170, 200, 25);
        add(idField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(320, 210, 120, 30);
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        add(submitButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(20, 20, 100, 30);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        add(backButton);

        submitButton.addActionListener(e -> {
            String empID = idField.getText().trim();

            try {
                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/your_db", "root",
                        "password");
                String sql = "SELECT name FROM employees WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, empID);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    String name = rs.getString("name");
                    JOptionPane.showMessageDialog(this, name + " Timed-in");
                } else {
                    JOptionPane.showMessageDialog(this, "Employee ID not found", "Error", JOptionPane.ERROR_MESSAGE);
                }

                rs.close();
                stmt.close();
                conn.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            // Go back to Attendance table view
            cardLayout.show(mainContainer, "table");
        });
    }
}