package Screens;

import Entity.User;
import Module.Registration.UserRegistration.UserRegistration;
import Algorithms.sha256;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays; // Import Arrays for comparing char arrays

public class Register extends JFrame {
    // Add JComboBox and JTextField as class members so they can be accessed in the action listener
    private JComboBox<String> securityQuestionComboBox;
    private JTextField securityAnswerField;
    private JPasswordField passwordField;
    private JPasswordField cpasswordField;
    private JTextField fnameField;
    private JTextField lnameField;
    private JTextField emailField;
    private JRadioButton hrButton;
    private JRadioButton accountantButton;

    public Register() {
        setTitle("SynergyGrafixCorp. Payroll Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        // Pack the frame and center it after all components are added
        pack();
        setLocationRelativeTo(null); // Center the window on the screen
    }

    private void initComponents() {
        // Panel to center the content
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add some padding

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
        mainPanel.add(Box.createVerticalStrut(20)); // Spacing after title

        // Center form (2 columns)
        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0)); // 2 columns with horizontal gap
        centerPanel.setMaximumSize(new Dimension(650, 300)); // Adjusted maximum width for new fields and longer dropdown
        centerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        centerPanel.setBackground(Color.LIGHT_GRAY);

        // ============= Left column =============
        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new BoxLayout(leftColumn, BoxLayout.Y_AXIS));
        leftColumn.setBackground(Color.LIGHT_GRAY);

        // First Name field
        fnameField = new JTextField(20);
        fnameField.setMaximumSize(new Dimension(250, 40)); // Increased height for better appearance
        fnameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        fnameField.setBorder(BorderFactory.createTitledBorder("First Name"));

        // Last Name field
        lnameField = new JTextField(20);
        lnameField.setMaximumSize(new Dimension(250, 40)); // Increased height
        lnameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        lnameField.setBorder(BorderFactory.createTitledBorder("Last Name"));

        // Position Radio Buttons
        hrButton = new JRadioButton("HR");
        accountantButton = new JRadioButton("Accountant");

        ButtonGroup positionGroup = new ButtonGroup();
        positionGroup.add(hrButton);
        positionGroup.add(accountantButton);

        // Style (optional)
        hrButton.setBackground(Color.LIGHT_GRAY);
        accountantButton.setBackground(Color.LIGHT_GRAY);
        hrButton.setForeground(Color.BLACK);
        accountantButton.setForeground(Color.BLACK);
        hrButton.setFocusPainted(false); // Remove focus border
        accountantButton.setFocusPainted(false);

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
        positionPanel.setPreferredSize(new Dimension(250, 70)); // Maintain preferred size

        // Add the radio buttons panel to the main panel
        positionPanel.add(radioPanel);


        leftColumn.add(fnameField);
        leftColumn.add(Box.createVerticalStrut(15)); // Increased spacing
        leftColumn.add(lnameField);
        leftColumn.add(Box.createVerticalStrut(15)); // Increased spacing
        leftColumn.add(positionPanel);

        // ============= Right column =============
        JPanel rightColumn = new JPanel();
        rightColumn.setLayout(new BoxLayout(rightColumn, BoxLayout.Y_AXIS));
        rightColumn.setBackground(Color.LIGHT_GRAY);

        // Email field
        emailField = new JTextField(20);
        emailField.setMaximumSize(new Dimension(250, 40)); // Increased height
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        // =============== Password field ===============
        // Create the password panel with titled border
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setMaximumSize(new Dimension(300, 50));
        passwordPanel.setBorder(BorderFactory.createTitledBorder("Password"));

        // Password field
        passwordField = new JPasswordField(20);
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
        // Create the confirm password panel with titled border
        JPanel cpasswordPanel = new JPanel();
        cpasswordPanel.setLayout(new BoxLayout(cpasswordPanel, BoxLayout.X_AXIS));
        cpasswordPanel.setMaximumSize(new Dimension(300, 50));
        cpasswordPanel.setBorder(BorderFactory.createTitledBorder("Confirm Password"));

        // Confirm Password field
        cpasswordField = new JPasswordField(20);
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

        // =============== Security Question Dropdown ===============
        String[] securityQuestions = {
                "Select a question", // Placeholder
                "What is the name of your favorite pet?",
                "What is the name of the town where you were born?",
                "What was your childhood nickname?",
                "What was the name of your first school?",
                "What is your dream vacation destination?"
        };
        securityQuestionComboBox = new JComboBox<>(securityQuestions);
        securityQuestionComboBox.setMaximumSize(new Dimension(400, 40)); // Increased width for full visibility
        securityQuestionComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        securityQuestionComboBox.setBorder(BorderFactory.createTitledBorder("Security Question"));
        ((JLabel)securityQuestionComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER); // Center text in combobox

        // =============== Security Answer Field ===============
        securityAnswerField = new JTextField(20);
        securityAnswerField.setMaximumSize(new Dimension(250, 40));
        securityAnswerField.setAlignmentX(Component.CENTER_ALIGNMENT);
        securityAnswerField.setBorder(BorderFactory.createTitledBorder("Security Answer"));

        rightColumn.add(emailField);
        rightColumn.add(Box.createVerticalStrut(15)); // Increased spacing
        rightColumn.add(passwordPanel);
        rightColumn.add(Box.createVerticalStrut(15)); // Increased spacing
        rightColumn.add(cpasswordPanel);
        rightColumn.add(Box.createVerticalStrut(15)); // Spacing before security question
        rightColumn.add(securityQuestionComboBox);
        rightColumn.add(Box.createVerticalStrut(15)); // Spacing before security answer
        rightColumn.add(securityAnswerField);


        centerPanel.add(leftColumn);
        centerPanel.add(rightColumn);
        mainPanel.add(centerPanel);
        mainPanel.add(Box.createVerticalStrut(30)); // Spacing before button

        // Buttons
        JButton regButton = new JButton("Register");
        Dimension buttonSize = new Dimension(200, 40); // Increased height for button
        regButton.setMaximumSize(buttonSize);
        regButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        regButton.setBackground(new Color(0, 153, 0));
        regButton.setForeground(Color.WHITE);
        regButton.setFocusPainted(false); // Remove focus border from button
        regButton.setFont(new Font("SansSerif", Font.BOLD, 16)); // Make button text a bit bigger

        regButton.addActionListener(e -> {
            String first_name = fnameField.getText().trim();
            String last_name = lnameField.getText().trim();
            String email = emailField.getText().trim();
            char[] passwordChar = passwordField.getPassword();
            char[] cpasswordChar = cpasswordField.getPassword();
            String securityQuestion = (String) securityQuestionComboBox.getSelectedItem();
            String securityAnswer = securityAnswerField.getText().trim();

            // --- Input Validation ---
            if (first_name.isEmpty() || last_name.isEmpty() || email.isEmpty() ||
                    passwordChar.length == 0 || cpasswordChar.length == 0 || securityAnswer.isEmpty() ||
                    (!hrButton.isSelected() && !accountantButton.isSelected())) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!Arrays.equals(passwordChar, cpasswordChar)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Input Error", JOptionPane.ERROR_MESSAGE);
                // Clear password fields for security
                passwordField.setText("");
                cpasswordField.setText("");
                return;
            }

            if (securityQuestion.equals("Select a question")) {
                JOptionPane.showMessageDialog(this, "Please select a security question.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Basic email format validation (can be more robust with regex)
            if (!email.matches("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                JOptionPane.showMessageDialog(this, "Invalid email format.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Basic password strength check (e.g., minimum 8 characters)
            if (passwordChar.length < 8) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long.", "Input Error", JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
                cpasswordField.setText("");
                return;
            }

            // Convert password char array to String after all checks
            String password = new String(passwordChar);
            sha256 sha256 = new sha256();
            String passwordHashed = sha256.stringToSHA256(password);

            // Hash security answer
            String securityAnswerHashed = sha256.stringToSHA256(securityAnswer);

            int access_level;
            if (hrButton.isSelected()) {
                access_level = 2; // HR access level
            } else {
                access_level = 3; // Accountant access level
            }
            int account_status = 0; // Default status, typically for new users
            String user_name = first_name; // Using first name as user_name for now

            // Create user object
            User user = new User(first_name, last_name, email, passwordHashed,
                    access_level, account_status, user_name);

            // Add security question and answer to user object
            user.setSecurityQuestion(securityQuestion);
            user.setSecurityAnswerHash(securityAnswerHashed);

            // Attempt to register user
            boolean registrationSuccess = UserRegistration.registerUser(user);

            if (registrationSuccess) {
                JOptionPane.showMessageDialog(this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Optionally clear fields or navigate to login screen
                fnameField.setText("");
                lnameField.setText("");
                emailField.setText("");
                passwordField.setText("");
                cpasswordField.setText("");
                securityQuestionComboBox.setSelectedIndex(0); // Reset dropdown
                securityAnswerField.setText("");
                positionGroup.clearSelection();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed. Email might already be registered or internal error.", "Registration Error", JOptionPane.ERROR_MESSAGE);
            }

            // Clear sensitive password data from memory
            Arrays.fill(passwordChar, ' ');
            Arrays.fill(cpasswordChar, ' ');
        });
        mainPanel.add(regButton);

        // Wrapper panel to center everything on the screen
        JPanel wrapper = new JPanel(new GridBagLayout());
        wrapper.setBackground(Color.WHITE);
        wrapper.add(mainPanel);

        add(wrapper);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Register homeScreen = new Register();
            homeScreen.setVisible(true);
            homeScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); // Ensure maximized mode
        });
    }
}
