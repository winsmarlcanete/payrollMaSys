package Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserRegConfirmation {

    public UserRegConfirmation() {
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
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                approvalMessage.setVisible(true); // Show the approval message
                continueButton.setEnabled(true); // Enable the continue button
            }
        });
        timer.setRepeats(false); // Run only once
        timer.start();

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
        new UserRegConfirmation();
    }
}
