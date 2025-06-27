package Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects; // Added for Objects.requireNonNull and potential future needs
import org.jdatepicker.impl.JDatePanelImpl; // Added for date picker functionality
import org.jdatepicker.impl.JDatePickerImpl; // Added for date picker functionality
import org.jdatepicker.impl.UtilDateModel; // Added for date picker functionality
import org.jdatepicker.impl.DateComponentFormatter; // Added for date picker functionality


public class AttendanceBackup extends JPanel {

    public AttendanceBackup(CardLayout cardLayout, JPanel mainContainer) {
        // Change to BorderLayout for easier centering of content
        setLayout(new BorderLayout());
        setBackground(new Color(34, 177, 76));

        // Create a panel to hold the main content (labels, text field, button)
        // This panel will be centered in the BorderLayout
        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false); // Make it transparent so the background color shows through
        contentPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for flexible centering

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Add some padding around components
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Center components within their grid cells

        JLabel titleLabel = new JLabel("Attendance Backup", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        // Set fill to HORIZONTAL to allow the label to take full width and center text
        gbc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(titleLabel, gbc);

        JLabel dateLabel = new JLabel(new SimpleDateFormat("MMMM d, yyyy").format(new Date()), SwingConstants.CENTER);
        dateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        dateLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        contentPanel.add(dateLabel, gbc);

        JLabel timeLabel = new JLabel(new SimpleDateFormat("h:mm a").format(new Date()), SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        timeLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        contentPanel.add(timeLabel, gbc);

        // Reset fill for subsequent components as they shouldn't stretch horizontally across the whole panel
        gbc.fill = GridBagConstraints.NONE;

        JLabel idLabel = new JLabel("Employee ID");
        idLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        contentPanel.add(idLabel, gbc);

        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(200, 25)); // Set preferred size for the text field
        idField.setHorizontalAlignment(JTextField.CENTER); // Center text within the field
        gbc.gridy++;
        contentPanel.add(idField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(120, 30)); // Set preferred size for the button
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        gbc.gridy++;
        contentPanel.add(submitButton, gbc);

        // Add the content panel to the CENTER of the main BorderLayout
        add(contentPanel, BorderLayout.CENTER);

        // Position the back button independently in the NORTHWEST (top-left)
        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        // Using FlowLayout for the top-left corner
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setOpaque(false);
        backButtonPanel.add(backButton);
        add(backButtonPanel, BorderLayout.NORTH);


        submitButton.addActionListener(e -> {
            String empID = idField.getText().trim();

            try {
                // IMPORTANT: Replace with your actual database connection details
                // Make sure your MySQL driver is in the classpath
                // For demonstration, using a dummy connection for compilation without actual DB
                // In a real application, you would manage this connection properly.
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
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            // Go back to Attendance table view
            if (cardLayout != null && mainContainer != null) {
                cardLayout.show(mainContainer, "table");
            } else {
                // Fallback for standalone testing or if cardLayout/mainContainer are null
                System.out.println("Back button pressed: cardLayout or mainContainer is null. Cannot switch view.");
            }
        });
    }

    public static void main(String[] args) {
        // For standalone testing of AttendanceBackup
        JFrame frame = new JFrame("Attendance Backup Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set a reasonable size for the frame
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Create dummy CardLayout and JPanel for testing purposes
        CardLayout testCardLayout = new CardLayout();
        JPanel testMainContainer = new JPanel(testCardLayout);

        // Add a dummy "table" panel to switch back to for testing the back button
        JPanel dummyTablePanel = new JPanel();
        dummyTablePanel.add(new JLabel("This is the main attendance table view."));
        testMainContainer.add(dummyTablePanel, "table");

        // Add the AttendanceBackup panel
        AttendanceBackup attendanceBackupPanel = new AttendanceBackup(testCardLayout, testMainContainer);
        testMainContainer.add(attendanceBackupPanel, "backup");

        // Show the AttendanceBackup panel initially
        testCardLayout.show(testMainContainer, "backup");

        frame.add(testMainContainer);
        frame.setVisible(true);
    }
}
