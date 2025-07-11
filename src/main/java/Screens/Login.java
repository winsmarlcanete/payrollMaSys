package Screens;

import Algorithms.sha256;
import Config.JDBC;
import Module.Registration.UserRegistration.UserRegistration;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import Module.Security.LevelofAcess;
import org.payroll.MainWindow;

import java.awt.*;
import java.sql.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// Make sure to correctly import your custom Rounded components based on their actual location:
import Components.RoundedTextField;
import Components.RoundedPasswordField;
import Components.RoundedButton;


import static Module.Registration.UserRegistration.UserRegistration.checkUserEmail;

public class Login extends JFrame {

    // Declare error message label at class level so action listeners can access it
    private JLabel errorMessageLabel;
    private RoundedTextField emailField;
    private RoundedPasswordField passwordField;
    private void performLogin() {
        String input_email = emailField.getText().trim();
        char[] passwordChars = passwordField.getPassword();

        // Input Validation for Empty Fields
        if (input_email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Email field cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
            errorMessageLabel.setVisible(false);
            return;
        }

        if (passwordChars.length == 0) {
            JOptionPane.showMessageDialog(this, "Password field cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
            errorMessageLabel.setVisible(false);
            return;
        }

        String passwordInput = sha256.stringToSHA256(new String(passwordChars));
        Arrays.fill(passwordChars, ' ');

        if(emailExists(input_email)) {
            String passwordFromDB = UserRegistration.getPasswordByEmail(input_email);
            if (Objects.equals(passwordFromDB, passwordInput)) {
                if (UserRegistration.isAccountActive(input_email)) {
                    errorMessageLabel.setVisible(false);
                    System.out.println("Email found and password matched!");

                    MainWindow mainWindow = new MainWindow(LevelofAcess.checkAccess(input_email));
                    mainWindow.setVisible(true);
                    mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    dispose();
                } else {
                    errorMessageLabel.setText("Account is inactive. Please contact admin.");
                    errorMessageLabel.setVisible(true);
                    System.out.println("Account is inactive.");
                }
            } else {
                errorMessageLabel.setText("Incorrect email or password");
                errorMessageLabel.setVisible(true);
                System.out.println("Email found but password incorrect!");
            }
        } else {
            errorMessageLabel.setText("Incorrect email or password");
            errorMessageLabel.setVisible(true);
            System.out.println("Couldn't find account");
        }
    }


    public Login() {
        setTitle("Synergy Grafix Corporation PMS");
        setMinimumSize(new Dimension(1020, 600));
        ImageIcon windowIcon = new ImageIcon(getClass().getClassLoader().getResource("logo_only.png"));
        setIconImage(windowIcon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set the background color for the entire frame's content pane
        getContentPane().setBackground(Color.LIGHT_GRAY);
        setLayout(new GridBagLayout()); // Use GridBagLayout for the frame to center the main panel
        initComponents();
    }

    private void initComponents() {
        // Main Panel for Content (this will be the white background card)
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setPreferredSize(new Dimension(550, 400));
        centerPanel.setMaximumSize(new Dimension(550, 400));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Logo
        int targetLogoHeight = 40;
        ImageIcon rawLogo = new ImageIcon(getClass().getClassLoader().getResource("whole_logo.png")); // Assuming whole_logo.png is the logo you want
        if (rawLogo.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Warning: Logo image 'whole_logo.png' not found or could not be loaded.");
            rawLogo = new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)); // Transparent 1x1 image
        }
        Image scaledLogo = rawLogo.getImage().getScaledInstance(
                (int) ((double) rawLogo.getIconWidth() / rawLogo.getIconHeight() * targetLogoHeight),
                targetLogoHeight,
                Image.SCALE_SMOOTH
        );
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // System Label
        JLabel systemLabel = new JLabel("Payroll Management System");
        systemLabel.setFont(new Font("Arial", Font.BOLD, 18));
        systemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Email field
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.X_AXIS));
        emailPanel.setPreferredSize(new Dimension(350, 50));
        emailPanel.setMaximumSize(new Dimension(350, 50));
        emailPanel.setBorder(BorderFactory.createTitledBorder("Email"));
        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailPanel.setBackground(Color.WHITE);

        emailField = new RoundedTextField(20);
        emailField.setPreferredSize(new Dimension(350, 30));
        emailField.setMaximumSize(new Dimension(350, 30));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)  // left padding of 10 pixels
        ));
        emailField.setBackground(Color.WHITE);

        emailPanel.add(emailField);

        // =============== Password field ===============
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setPreferredSize(new Dimension(350, 50));
        passwordPanel.setMaximumSize(new Dimension(350, 50));
        passwordPanel.setBorder(BorderFactory.createTitledBorder("Password"));
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordPanel.setBackground(Color.WHITE);

        // Password field
        passwordField = new RoundedPasswordField(20);
        passwordField.setPreferredSize(new Dimension(300, 30));
        passwordField.setMaximumSize(new Dimension(300, 30));
        passwordField.setEchoChar('•');
//        passwordField.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 10)  // left padding of 10 pixels
        ));
        passwordField.setBackground(Color.WHITE);

        // Eye button
        JButton toggleButton = new JButton("👁");
        toggleButton.setFocusable(false);
        toggleButton.setMargin(new Insets(0, 5, 0, 5));
        toggleButton.setPreferredSize(new Dimension(40, 30));
        toggleButton.setBackground(new Color(220, 220, 220));
        toggleButton.setBorderPainted(false);
        toggleButton.setOpaque(false);

        // Toggle password visibility
        toggleButton.addActionListener(e -> {
            if (passwordField.getEchoChar() == 0) {
                passwordField.setEchoChar('•');
                toggleButton.setText("👁");
            } else {
                passwordField.setEchoChar((char) 0);
                toggleButton.setText("✖");
            }
        });

        passwordPanel.add(Box.createHorizontalStrut(5));
        passwordPanel.add(passwordField);
        passwordPanel.add(Box.createHorizontalStrut(5));
        passwordPanel.add(toggleButton);
        passwordPanel.add(Box.createHorizontalStrut(5));

        // Error Message Label (Initially hidden)
        errorMessageLabel = new JLabel("Incorrect email or password");
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorMessageLabel.setVisible(false);

        // Add DocumentListeners to hide error message on input
        DocumentListener hideErrorMessageListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { hideError(); }
            @Override
            public void removeUpdate(DocumentEvent e) { hideError(); }
            @Override
            public void changedUpdate(DocumentEvent e) { hideError(); }

            private void hideError() {
                if (errorMessageLabel.isVisible()) {
                    errorMessageLabel.setVisible(false);
                }
            }
        };
        emailField.getDocument().addDocumentListener(hideErrorMessageListener);
        passwordField.getDocument().addDocumentListener(hideErrorMessageListener);


        // Buttons
        RoundedButton loginButton = new RoundedButton("Log In", 20);
        RoundedButton createAccountButton = new RoundedButton("Create Account", 20);
        RoundedButton forgotPasswordButton = new RoundedButton("Forgot Password", 20);

        Dimension buttonSize = new Dimension(350, 30);

        loginButton.setPreferredSize(buttonSize);
        createAccountButton.setPreferredSize(buttonSize);
        forgotPasswordButton.setPreferredSize(buttonSize);

        loginButton.setMaximumSize(buttonSize);
        createAccountButton.setMaximumSize(buttonSize);
        forgotPasswordButton.setMaximumSize(buttonSize);

        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createAccountButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        forgotPasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createAccountButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.setBackground(new Color(46, 204, 113));
        createAccountButton.setBackground(new Color(46, 204, 113));
        forgotPasswordButton.setBackground(new Color(46, 204, 113));

        loginButton.setForeground(Color.WHITE);
        createAccountButton.setForeground(Color.WHITE);
        forgotPasswordButton.setForeground(Color.WHITE);

        //Action Listener
        loginButton.addActionListener(e -> performLogin());

        KeyListener enterKeyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        };

        emailField.addKeyListener(enterKeyListener);
        passwordField.addKeyListener(enterKeyListener);

        // Create Account Button ActionListener
        createAccountButton.addActionListener(e -> {
            Register registerScreen = new Register();
            registerScreen.setVisible(true);
            registerScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
            dispose();
        });

        // Forgot Password Button will redirect to ResetPassword.java
        forgotPasswordButton.addActionListener(e -> {
            ResetPassword.main(new String[] {});
            dispose();
        });

        // Add components to the panel
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(logoLabel);
        centerPanel.add(systemLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        centerPanel.add(emailPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(passwordPanel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(errorMessageLabel);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        centerPanel.add(loginButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(createAccountButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(forgotPasswordButton);
        centerPanel.add(Box.createVerticalGlue());

        // Add the main panel to the frame using GridBagLayout to center it
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(centerPanel, gbc);

        pack();
        setLocationRelativeTo(null);
    }

    public static boolean emailExists(String targetEmail) {
        List<String> emails = UserRegistration.checkUserEmail();
        for (String email : emails) {
            if (email.equalsIgnoreCase(targetEmail)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginScreen = new Login();
            loginScreen.setVisible(true);
            loginScreen.setExtendedState(JFrame.MAXIMIZED_BOTH);
        });
    }
}