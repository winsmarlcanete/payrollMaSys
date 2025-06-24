package Screens;

import Config.JDBC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRegConfirmation {

    public UserRegConfirmation(String userEmail) {
        // Create the frame
        JFrame frame = new JFrame("SynergyGrafixCorp - User Registration");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create the main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.LIGHT_GRAY);

        // Set a preferred size for the panel
        panel.setPreferredSize(new Dimension(400, 300));

        // Add the logo and title
        JLabel logo = new JLabel("SynergyGrafixCorp.");
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setForeground(new Color(0, 153, 0));
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20)); // Add spacing
        panel.add(logo);

        // Add the subtitle
        JLabel subtitle = new JLabel("User Registration");
        subtitle.setFont(new Font("Arial", Font.BOLD, 16));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(10)); // Add spacing
        panel.add(subtitle);

        // Add the message
        JLabel message = new JLabel("Account registered. Waiting for admin approval.");
        message.setFont(new Font("Arial", Font.PLAIN, 14));
        message.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20)); // Add spacing
        panel.add(message);

        // Add the approval message (initially hidden) above the continue button
        JLabel approvalMessage = new JLabel("Account approved.");
        approvalMessage.setFont(new Font("Arial", Font.BOLD, 14));
        approvalMessage.setForeground(Color.BLUE);
        approvalMessage.setAlignmentX(Component.CENTER_ALIGNMENT);
        approvalMessage.setVisible(false); // Initially hidden
        panel.add(Box.createVerticalStrut(10)); // Add spacing
        panel.add(approvalMessage, panel.getComponentCount() - 1);

        // Add the disabled button
        JButton continueButton = new JButton("Continue");
        continueButton.setEnabled(false);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(Box.createVerticalStrut(20)); // Add spacing
        panel.add(continueButton);

        // Simulate backend approval (for demonstration purposes)
        new Thread(() -> {
            try (Connection conn = JDBC.getConnection()) {
                while (true) {
                    String query = "SELECT account_status FROM users WHERE email = ?";
                    PreparedStatement stmt = conn.prepareStatement(query);
                    stmt.setString(1, userEmail);

                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        int accountStatus = rs.getInt("account_status");
                        if (accountStatus == 1) {
                            SwingUtilities.invokeLater(() -> {
                                approvalMessage.setVisible(true); // Show the approval message
                                continueButton.setEnabled(true); // Enable the continue button

                                continueButton.addActionListener(e -> {
                                    frame.dispose(); // Close the current frame
                                    new Login(); // Navigate to the login screen
                                });
                            });
                            break; // Exit the loop when account_status is 1
                        }
                    }
                    Thread.sleep(5000); // Wait for 5 seconds before checking again
                }
            } catch (SQLException | InterruptedException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Failed to check account status.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();

        // Add the panel to the frame
        frame.add(panel, BorderLayout.CENTER);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Maximize the frame
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Center the panel/components horizontally and vertically
        frame.setLayout(new GridBagLayout());
        frame.add(panel, new GridBagConstraints());

        // Make the frame visible
        frame.setVisible(true);
    }

    public static void main(String[] args) {

    }
}
