package Screens;

import Algorithms.sha256;
import Module.Registration.UserRegistration.UserRegistration;

import javax.swing.*;

import org.payroll.MainWindow;

import java.awt.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import static Module.Registration.UserRegistration.UserRegistration.checkUserEmail;

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
        ImageIcon rawLogo = new ImageIcon("C:/Users/PC1/Desktop/SynRepo/payrollMaSys/src/main/java/Screens/logo.png");
        Image scaledLogo = rawLogo.getImage().getScaledInstance(300, 70, Image.SCALE_SMOOTH); // Fixed size
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // System Label
        JLabel systemLabel = new JLabel("Payroll Management System");
        systemLabel.setFont(new Font("Arial", Font.BOLD, 18));
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
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Ensure password panel is centered

        // Buttons
        JButton loginButton = new JButton("Log In");
        JButton createAccountButton = new JButton("Create Account");
        JButton forgotPasswordButton = new JButton("Forgot Password");


        //Action Listener
        loginButton.addActionListener(e -> {
            String input_email = emailField.getText();
            if(emailExists(input_email)) {
                System.out.print("Email found");
                String passwordFromDB = UserRegistration.getPasswordByEmail(input_email);
                char[] passwordChars = passwordField.getPassword();
                String passwordInput = sha256.stringToSHA256(new String(passwordChars));
                if (Objects.equals(passwordFromDB, passwordInput)){
                    System.out.println(" and password matched!");

                    MainWindow mainWindow = new MainWindow();
                    mainWindow.setVisible(true);
                    mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    dispose();


                } else {
                    System.out.println(" but password incorrect!");
                }
            } else {
                System.out.println("Couldn't find account");
            }
        });



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

        // Forgot Password Button will redirect to ResetPassword.java
        forgotPasswordButton.addActionListener(e -> {
            ResetPassword.main(new String[] {});
            dispose();
        });

        // Add components to the panel
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(logoLabel);
        centerPanel.add(systemLabel); // Now systemLabel is declared before being added
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
        pack(); // Call pack() after adding components
    }

    public static boolean emailExists(String targetEmail) {
        List<String> emails = checkUserEmail();
        for (String email : emails) {
            if (email.equalsIgnoreCase(targetEmail)) {
                return true; // Found a match
            }
        }
        return false; // No match found
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login homeScreen = new Login();
            homeScreen.setVisible(true);
            homeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); // Ensure maximized mode
        });
    }
}
