package Screens;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    public Login() {
        setTitle("SynergyGrafixCorp. Payroll Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Panel to center the content
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.LIGHT_GRAY);
        centerPanel.setPreferredSize(new Dimension(400, 350));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Logo
        JLabel logoLabel = new JLabel("SynergyGrafixCorp.");
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setForeground(new Color(0, 153, 0)); // Greenish

        JLabel systemLabel = new JLabel("Payroll Management System");
        systemLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        systemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Email field
        JTextField emailField = new JTextField(20);
        emailField.setMaximumSize(new Dimension(250, 30));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        // =============== Password field =============== 
        // Create the password panel with titled border
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setMaximumSize(new Dimension(250, 50));
        passwordPanel.setBorder(BorderFactory.createTitledBorder("Password"));

        // Password field
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(250, 30));
        passwordField.setEchoChar('•');

        // Eye button
        JButton toggleButton = new JButton("👁"); // You can also use an icon
        toggleButton.setFocusable(false);
        toggleButton.setMargin(new Insets(0, 5, 0, 5));
        toggleButton.setPreferredSize(new Dimension(40, 30));

        // Toggle password visibility
        toggleButton.addActionListener(e -> {
            if (passwordField.getEchoChar() == 0) {
                passwordField.setEchoChar('•'); // Hide
            } else {
                passwordField.setEchoChar((char) 0); // Show
            }
        });

        passwordPanel.add(Box.createHorizontalStrut(5)); // spacing from left
        passwordPanel.add(passwordField);
        passwordPanel.add(Box.createHorizontalStrut(5));
        passwordPanel.add(toggleButton);
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Buttons
        JButton loginButton = new JButton("Log In");
        JButton createAccountButton = new JButton("Create Account");
        JButton forgotPasswordButton = new JButton("Forgot Password");

        Dimension buttonSize = new Dimension(200, 30);
        loginButton.setMaximumSize(buttonSize);
        createAccountButton.setMaximumSize(buttonSize);
        forgotPasswordButton.setMaximumSize(buttonSize);

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.setBackground(new Color(0, 153, 0));
        createAccountButton.setBackground(new Color(0, 204, 0));
        forgotPasswordButton.setBackground(new Color(0, 204, 0));

        loginButton.setForeground(Color.WHITE);
        createAccountButton.setForeground(Color.WHITE);
        forgotPasswordButton.setForeground(Color.WHITE);

        // Create Account Button ActionListener
        createAccountButton.addActionListener(e -> {
            // Open the registration screen
            Register registerScreen = new Register();
            registerScreen.setVisible(true);
            registerScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); // Ensure maximized mode
            dispose(); // Close the login screen
        });

        // Add components to the panel
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(logoLabel);
        centerPanel.add(systemLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(emailField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(passwordPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(loginButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(createAccountButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(forgotPasswordButton);

        // Wrapper panel to center everything on the screen
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(centerPanel);

        add(wrapper);
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login homeScreen = new Login();
            homeScreen.setVisible(true);
            homeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); // Ensure maximized mode
        });
    }
}
