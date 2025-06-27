package Screens;

import Module.Attendance.Backup.AttendanceBackup;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceBackupScreen extends JPanel {

    public AttendanceBackupScreen(CardLayout cardLayout, JPanel mainContainer) {
        // Change to BorderLayout for easier centering of content
        setLayout(new BorderLayout());
        setBackground(new Color(34, 177, 76));

        // Create a panel to hold the main content (labels, text field, button)
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

        gbc.fill = GridBagConstraints.NONE;

        JLabel idLabel = new JLabel("Employee ID");
        idLabel.setForeground(Color.WHITE);
        gbc.gridy++;
        contentPanel.add(idLabel, gbc);

        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(200, 25));
        idField.setHorizontalAlignment(JTextField.CENTER);
        gbc.gridy++;
        contentPanel.add(idField, gbc);

        JButton submitButton = new JButton("Submit");
        submitButton.setPreferredSize(new Dimension(120, 30));
        submitButton.setBackground(Color.BLACK);
        submitButton.setForeground(Color.WHITE);
        gbc.gridy++;
        contentPanel.add(submitButton, gbc);

        add(contentPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.setOpaque(false);
        backButtonPanel.add(backButton);
        add(backButtonPanel, BorderLayout.NORTH);

        submitButton.addActionListener(e -> {
            String empID = idField.getText().trim();

            try {
                int employeeId = Integer.parseInt(empID);
                String result = AttendanceBackup.processTimeCard(employeeId);
                JOptionPane.showMessageDialog(this, result, "Attendance Status", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Employee ID. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backButton.addActionListener(e -> {
            cardLayout.show(mainContainer, "AttendanceScreen");
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Attendance Backup Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        CardLayout testCardLayout = new CardLayout();
        JPanel testMainContainer = new JPanel(testCardLayout);

        JPanel dummyTablePanel = new JPanel();
        dummyTablePanel.add(new JLabel("This is the main attendance table view."));
        testMainContainer.add(dummyTablePanel, "table");

        AttendanceBackupScreen attendanceBackupPanel = new AttendanceBackupScreen(testCardLayout, testMainContainer);
        testMainContainer.add(attendanceBackupPanel, "backup");

        testCardLayout.show(testMainContainer, "backup");

        frame.add(testMainContainer);
        frame.setVisible(true);
    }
}