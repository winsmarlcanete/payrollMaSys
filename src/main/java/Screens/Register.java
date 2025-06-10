package Screens;

import Entity.User;
import Module.Registration.UserRegistration.UserRegistration;
import Algorithms.sha256;

import javax.swing.*;

import java.awt.*;
// import java.time.LocalDate;

public class Register extends JFrame {
    public Register() {
        setTitle("SynergyGrafixCorp. Payroll Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
    }

    private void initComponents() {
        // Panel to center the content
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Logo
        JLabel logoLabel = new JLabel("SynergyGrafixCorp.");
        logoLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        logoLabel.setForeground(new Color(0, 153, 0)); // Greenish

        JLabel systemLabel = new JLabel("User Registration");
        systemLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        systemLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(logoLabel);
        mainPanel.add(systemLabel);

        // Center form (2 columns)
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        centerPanel.setMaximumSize(new Dimension(450, 150));
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.setBackground(Color.LIGHT_GRAY);

        // ============= Left column =============
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        leftColumn.setBackground(Color.LIGHT_GRAY);

        // First Name field
        JTextField fnameField = new JTextField(20);
        fnameField.setMaximumSize(new Dimension(250, 30));
        fnameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        fnameField.setBorder(BorderFactory.createTitledBorder("First Name"));
        
        // Last Name field
        JTextField lnameField = new JTextField(20);
        lnameField.setMaximumSize(new Dimension(250, 30));
        lnameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        lnameField.setBorder(BorderFactory.createTitledBorder("Last Name"));

        // Position
        JRadioButton hrButton = new JRadioButton("HR");
        JRadioButton accountantButton = new JRadioButton("Accountant");

        ButtonGroup positionGroup = new ButtonGroup();
        positionGroup.add(hrButton);
        positionGroup.add(accountantButton);

        // Style (optional)
        hrButton.setBackground(Color.LIGHT_GRAY);
        accountantButton.setBackground(Color.LIGHT_GRAY);
        hrButton.setForeground(Color.BLACK);
        accountantButton.setForeground(Color.BLACK);

        // Subpanel for radio buttons side-by-side
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // side by side with spacing
        radioPanel.setBackground(Color.LIGHT_GRAY);
        radioPanel.add(hrButton);
        radioPanel.add(accountantButton);

        // Main position panel with titled border
        JPanel positionPanel = new JPanel();
        positionPanel.setLayout(new BoxLayout(positionPanel, BoxLayout.Y_AXIS));
        positionPanel.setBackground(Color.LIGHT_GRAY);
        positionPanel.setBorder(BorderFactory.createTitledBorder("Position"));
        positionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        positionPanel.setMaximumSize(new Dimension(250, 70));
        positionPanel.setPreferredSize(new Dimension(250, 70));

        // Add the radio buttons panel to the main panel
        positionPanel.add(radioPanel);


        leftColumn.add(fnameField);
        leftColumn.add(Box.createVerticalStrut(10));
        leftColumn.add(lnameField);
        leftColumn.add(Box.createVerticalStrut(10));
        leftColumn.add(positionPanel);

        // ============= Right column =============
        JPanel rightColumn = new JPanel();
        rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
        rightColumn.setBackground(Color.LIGHT_GRAY);

        // Email field
        JTextField emailField = new JTextField(20);
        emailField.setMaximumSize(new Dimension(250, 40));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        // =============== Password field =============== 
        // Create the password panel with titled border
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setMaximumSize(new Dimension(300, 50));
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


        // =============== Confirm Password field ===============
        // Create the password panel with titled border
        JPanel cpasswordPanel = new JPanel();
        cpasswordPanel.setLayout(new BoxLayout(cpasswordPanel, BoxLayout.X_AXIS));
        cpasswordPanel.setMaximumSize(new Dimension(300, 50));
        cpasswordPanel.setBorder(BorderFactory.createTitledBorder("Confirm Password"));

        // Password field
        JPasswordField cpasswordField = new JPasswordField(20);
        cpasswordField.setMaximumSize(new Dimension(250, 30));
        cpasswordField.setEchoChar('•');

        // Eye button
        JButton ctoggleButton = new JButton("👁"); // You can also use an icon
        ctoggleButton.setFocusable(false);
        ctoggleButton.setMargin(new Insets(0, 5, 0, 5));
        ctoggleButton.setPreferredSize(new Dimension(40, 30));

        // Toggle password visibility
        ctoggleButton.addActionListener(e -> {
            if (cpasswordField.getEchoChar() == 0) {
                cpasswordField.setEchoChar('•'); // Hide
            } else {
                cpasswordField.setEchoChar((char) 0); // Show
            }
        });

        cpasswordPanel.add(Box.createHorizontalStrut(5)); // spacing from left
        cpasswordPanel.add(cpasswordField);
        cpasswordPanel.add(Box.createHorizontalStrut(5));
        cpasswordPanel.add(ctoggleButton);
        cpasswordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        rightColumn.add(emailField);
        rightColumn.add(Box.createVerticalStrut(10));
        rightColumn.add(passwordPanel);
        rightColumn.add(Box.createVerticalStrut(10));
        rightColumn.add(cpasswordPanel);

        centerPanel.add(leftColumn);
        centerPanel.add(rightColumn);
        mainPanel.add(centerPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Buttons
        JButton regButton = new JButton("Register");
        Dimension buttonSize = new Dimension(200, 30);
        regButton.setMaximumSize(buttonSize);
        regButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        regButton.setBackground(new Color(0, 153, 0));
        regButton.setForeground(Color.WHITE);

        regButton.addActionListener(e -> {
            String first_name = fnameField.getText();
            String last_name = lnameField.getText();
            String email = emailField.getText();
            char[] passwordChar = passwordField.getPassword();
            String password = new String(passwordChar);
            sha256 sha256 = new sha256();
            String passwordHashed = sha256.stringToSHA256(password);
            int access_level;
            if(hrButton.isSelected()){
                access_level = 2;
            } else {
                access_level = 3;
            }
            int account_status = 0;
            String user_name = fnameField.getText();

            User user = new User(first_name,last_name,email,passwordHashed,
                    access_level,account_status,user_name);

            UserRegistration.registerUser(user);


        });
        mainPanel.add(regButton);

        // Wrapper panel to center everything on the screen
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(mainPanel);

        add(wrapper);
        pack();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Register homeScreen = new Register();
            homeScreen.setVisible(true);
            homeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); // Ensure maximized mode
        });
    }
}
