package Screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage; // Added this import just in case for logo fallback
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Algorithms.OTPGenerator;
import Config.EmailService;
import Config.JDBC;
import Module.Security.ForgotPassword;
import Module.Security.UserAuthenticator;


public class ResetPassword {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static String userEmail; // To carry the email across screens

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ResetPassword().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame();
        frame.setTitle("Synergy Grafix Corporation PMS");
        frame.setMinimumSize(new Dimension(1020, 600));
        ImageIcon windowIcon = new ImageIcon(getClass().getClassLoader().getResource("logo_only.png"));
        frame.setIconImage(windowIcon.getImage());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new GridBagLayout()); // Using GridBagLayout for the frame to position the panel centrally

        // --- Main Panel for Content ---
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE); // Changed to White to match image's inner panel background
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding inside the panel

        // Set preferred/maximum size for the panel to make it look like a card
        panel.setPreferredSize(new Dimension(550, 500));
        panel.setMaximumSize(new Dimension(550, 500));

        // --- Back Button (Top Left of the panel) ---
        // Using a JPanel to hold the back button and push it to the left
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE); // Match panel background

        JButton backButton = new JButton("<html>&#x2190;</html>"); // Unicode left arrow
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Indicate clickable
        backButton.addActionListener(e -> {
            frame.dispose();
            // Navigate back to the LoginScreen
            // Assuming Login.java has a main method to start it
            Login.main(null); // This call assumes your external Login.java exists and can be invoked
        });
        headerPanel.add(backButton);
        panel.add(headerPanel);

        // Add rigid area after header panel for spacing from logo
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Logo - Dynamically scaled from classpath
        int targetHeight = 30; // Consistent height for logos across screens
        ImageIcon logoIcon;
        java.net.URL logoImageUrl = getClass().getClassLoader().getResource("whole_logo.png");
        if (logoImageUrl != null) {
            logoIcon = new ImageIcon(logoImageUrl);
            // Fallback for logo if not found by MediaTracker (though URL null check is primary)
            if (logoIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                logoIcon = new ImageIcon(new BufferedImage(targetHeight, targetHeight, BufferedImage.TYPE_INT_ARGB)); // Transparent placeholder
            }
        } else {
            logoIcon = new ImageIcon(new BufferedImage(targetHeight, targetHeight, BufferedImage.TYPE_INT_ARGB)); // Transparent placeholder
        }

        int origWidth = logoIcon.getIconWidth();
        int origHeight = logoIcon.getIconHeight();
        // Prevent division by zero if image is 0 height
        int targetWidth = (origHeight > 0) ? (int) ((double) origWidth / origHeight * targetHeight) : targetHeight;
        Image scaledLogo = logoIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the logo
        panel.add(logoLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space between logo and title

        JLabel title = new JLabel("Payroll Management System");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(emailLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        RoundedTextField emailField = new RoundedTextField(20);
        emailField.setMaximumSize(new Dimension(350, 30));
        emailField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(emailField);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); // Space before buttons

        // --- Buttons for Password Reset Options ---
        RoundedButton sendCodeButton = new RoundedButton("Send Reset Code", 20);
        sendCodeButton.setBackground(new Color(46, 204, 113));
        sendCodeButton.setForeground(Color.WHITE);
        sendCodeButton.setMaximumSize(new Dimension(350, 30));
        sendCodeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        sendCodeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sendCodeButton.addActionListener(e -> {
            String email = emailField.getText();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your email.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!email.matches(EMAIL_PATTERN)) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                userEmail = email; // Store email for the next screen
                String otp = OTPGenerator.generateOTP(); // Generate OTP
                EmailService.sendEmail(userEmail, otp); // Send email with OTP
                ForgotPassword.saveOtp(userEmail, otp); // Save OTP (presumably to DB)
                JOptionPane.showMessageDialog(frame, "Reset code sent to your email.", "Code Sent", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose(); // Close current frame
                EnterCodeScreen.showEnterCodeScreen(); // Open the new Enter Code screen
            }
        });
        sendCodeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                sendCodeButton.setBackground(new Color(39, 174, 96)); // Darker green on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                sendCodeButton.setBackground(new Color(46, 204, 113)); // Original green
            }
            @Override
            public void mousePressed(MouseEvent e) {
                sendCodeButton.setBackground(new Color(33, 148, 82)); // Even darker when pressed
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                sendCodeButton.setBackground(new Color(39, 174, 96)); // Back to hover color
            }
        });

        panel.add(sendCodeButton);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between buttons

        // --- "or" Separator ---
        JLabel orLabel = new JLabel("or");
        orLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        orLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(orLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Space after "or"

        RoundedButton answerSecurityQuestionButton = new RoundedButton("Answer Security Question", 20);
        answerSecurityQuestionButton.setBackground(new Color(46, 204, 113)); // Matched green color
        answerSecurityQuestionButton.setForeground(Color.WHITE);
        answerSecurityQuestionButton.setMaximumSize(new Dimension(350, 30));
        answerSecurityQuestionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        answerSecurityQuestionButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        answerSecurityQuestionButton.addActionListener(e -> {
            String email = emailField.getText();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your email to answer security question.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!email.matches(EMAIL_PATTERN)) {
                JOptionPane.showMessageDialog(frame, "Please enter a valid email address.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                userEmail = email; // Store email for the next screen
                frame.dispose(); // Close current frame
                SecurityQuestionScreen.showSecurityQuestionScreen(); // Open the new Security Question screen
            }
        });
        answerSecurityQuestionButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                answerSecurityQuestionButton.setBackground(new Color(39, 174, 96)); // Darker green on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                answerSecurityQuestionButton.setBackground(new Color(46, 204, 113)); // Original green
            }
            @Override
            public void mousePressed(MouseEvent e) {
                answerSecurityQuestionButton.setBackground(new Color(33, 148, 82)); // Even darker when pressed
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                answerSecurityQuestionButton.setBackground(new Color(39, 174, 96)); // Back to hover color
            }
        });

        panel.add(answerSecurityQuestionButton);
        // --- End New Buttons ---

        // Add the main panel to the frame using GridBagLayout to center it
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(panel, gbc);
        frame.setVisible(true);
    }
}

// ----------- EnterCodeScreen Class ------------
class EnterCodeScreen {
    public static void showEnterCodeScreen() {
        JFrame frame = new JFrame("Enter Reset Code");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE); // Changed to White to match image's inner panel background
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(550, 500));
        panel.setMaximumSize(new Dimension(550, 500));

        // Back Button
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        JButton backButton = new JButton("<html>&#x2190;</html>");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            frame.dispose();
            ResetPassword.main(null); // Go back to the ResetPassword screen
        });
        headerPanel.add(backButton);
        panel.add(headerPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));


        // Logo - Dynamically scaled from classpath
        int targetHeight = 30;
        ImageIcon logoIcon;
        java.net.URL logoImageUrl = EnterCodeScreen.class.getClassLoader().getResource("whole_logo.png");
        if (logoImageUrl != null) {
            logoIcon = new ImageIcon(logoImageUrl);
            if (logoIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                logoIcon = new ImageIcon(new BufferedImage(targetHeight, targetHeight, BufferedImage.TYPE_INT_ARGB)); // Transparent placeholder
            }
        } else {
            logoIcon = new ImageIcon(new BufferedImage(targetHeight, targetHeight, BufferedImage.TYPE_INT_ARGB)); // Transparent placeholder
        }

        int origWidth = logoIcon.getIconWidth();
        int origHeight = logoIcon.getIconHeight();
        int targetWidth = (origHeight > 0) ? (int) ((double) origWidth / origHeight * targetHeight) : targetHeight;
        Image scaledLogo = logoIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel title = new JLabel("Payroll Management System");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel codeLabel = new JLabel("Enter Code");
        codeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(codeLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        RoundedTextField codeField = new RoundedTextField(20);
        codeField.setMaximumSize(new Dimension(350, 30));
        codeField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(codeField);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        RoundedButton verifyButton = new RoundedButton("Verify Code", 20);
        verifyButton.setBackground(new Color(46, 204, 113));
        verifyButton.setForeground(Color.WHITE);
        verifyButton.setMaximumSize(new Dimension(350, 30));
        verifyButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        verifyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        verifyButton.addActionListener(e -> {
            String enteredCode = codeField.getText();
            if (enteredCode.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter the code.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (ForgotPassword.verifyOtp(ResetPassword.userEmail, enteredCode)) {
                    frame.dispose(); // Close current frame
                    NewPasswordScreen.showNewPasswordScreen(); // Open the new New Password screen
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    codeField.setText(""); // Clear the field for re-entry
                }
            }
        });
        verifyButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                verifyButton.setBackground(new Color(39, 174, 96)); // Darker green on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                verifyButton.setBackground(new Color(46, 204, 113)); // Original green
            }
            @Override
            public void mousePressed(MouseEvent e) {
                verifyButton.setBackground(new Color(33, 148, 82)); // Even darker when pressed
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                verifyButton.setBackground(new Color(39, 174, 96)); // Back to hover color
            }
        });

        panel.add(verifyButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(panel, gbc);
        frame.setVisible(true);
    }
}

// ----------- SecurityQuestionScreen Class ------------
class SecurityQuestionScreen {
    public static void showSecurityQuestionScreen() {
        JFrame frame = new JFrame("Security Question");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(550, 500));
        panel.setMaximumSize(new Dimension(550, 500));

        // Back Button
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        JButton backButton = new JButton("<html>&#x2190;</html>");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            frame.dispose();
            ResetPassword.main(null); // Go back to the ResetPassword screen
        });
        headerPanel.add(backButton);
        panel.add(headerPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Dropdown for Security Questions
        JLabel questionLabel = new JLabel("Select Security Question:");
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(questionLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JComboBox<String> securityQuestionDropdown = new JComboBox<>();
        securityQuestionDropdown.setMaximumSize(new Dimension(350, 30));
        securityQuestionDropdown.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Populate dropdown with questions from the database
        try (Connection conn = JDBC.getConnection()) {
            String query = "SELECT DISTINCT security_question FROM users WHERE security_question IS NOT NULL";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            ArrayList<String> questions = new ArrayList<>();
            while (rs.next()) {
                questions.add(rs.getString("security_question"));
            }

            for (String question : questions) {
                securityQuestionDropdown.addItem(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Failed to load security questions.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        panel.add(securityQuestionDropdown);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Text field for entering the answer
        JLabel answerLabel = new JLabel("Enter Your Answer:");
        answerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        answerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(answerLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        JTextField answerField = new JTextField();
        answerField.setMaximumSize(new Dimension(350, 30));
        answerField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(answerField);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        RoundedButton submitAnswerButton = new RoundedButton("Submit Answer", 20);
        submitAnswerButton.setBackground(new Color(46, 204, 113));
        submitAnswerButton.setForeground(Color.WHITE);
        submitAnswerButton.setMaximumSize(new Dimension(350, 30));
        submitAnswerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitAnswerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        submitAnswerButton.addActionListener(e -> {
            String selectedQuestion = (String) securityQuestionDropdown.getSelectedItem();
            String enteredAnswer = answerField.getText();

            if (enteredAnswer == null || enteredAnswer.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your answer.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean isAnswerCorrect = ForgotPassword.verifySecurityAnswer(ResetPassword.userEmail, enteredAnswer);
                if (isAnswerCorrect) {
                    JOptionPane.showMessageDialog(frame, "Answer submitted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose();
                    NewPasswordScreen.showNewPasswordScreen();
                } else {
                    JOptionPane.showMessageDialog(frame, "Incorrect answer. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        submitAnswerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                submitAnswerButton.setBackground(new Color(39, 174, 96)); // Darker green on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                submitAnswerButton.setBackground(new Color(46, 204, 113)); // Original green
            }
            @Override
            public void mousePressed(MouseEvent e) {
                submitAnswerButton.setBackground(new Color(33, 148, 82)); // Even darker when pressed
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                submitAnswerButton.setBackground(new Color(39, 174, 96)); // Back to hover color
            }
        });

        panel.add(submitAnswerButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(panel, gbc);
        frame.setVisible(true);
    }

}

// ----------- NewPasswordScreen Class ------------
class NewPasswordScreen {
    public static void showNewPasswordScreen() {
        JFrame frame = new JFrame("Set New Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(550, 500));
        panel.setMaximumSize(new Dimension(550, 500));

        // Back Button
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        JButton backButton = new JButton("<html>&#x2190;</html>");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            frame.dispose();
            ResetPassword.main(null); // Go back to the initial ResetPassword screen
        });
        headerPanel.add(backButton);
        panel.add(headerPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Logo - Dynamically scaled from classpath
        int targetHeight = 30;
        ImageIcon logoIcon;
        java.net.URL logoImageUrl = NewPasswordScreen.class.getClassLoader().getResource("whole_logo.png");
        if (logoImageUrl != null) {
            logoIcon = new ImageIcon(logoImageUrl);
            if (logoIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                logoIcon = new ImageIcon(new BufferedImage(targetHeight, targetHeight, BufferedImage.TYPE_INT_ARGB)); // Transparent placeholder
            }
        } else {
            logoIcon = new ImageIcon(new BufferedImage(targetHeight, targetHeight, BufferedImage.TYPE_INT_ARGB)); // Transparent placeholder
        }

        int origWidth = logoIcon.getIconWidth();
        int origHeight = logoIcon.getIconHeight();
        int targetWidth = (origHeight > 0) ? (int) ((double) origWidth / origHeight * targetHeight) : targetHeight;
        Image scaledLogo = logoIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel title = new JLabel("Payroll Management System");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // New Password Label and Field
        JLabel newPasswordLabel = new JLabel("New Password");
        newPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(newPasswordLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panel for new password field and toggle button
        JPanel newPassPanel = new JPanel();
        newPassPanel.setLayout(new BoxLayout(newPassPanel, BoxLayout.X_AXIS));
        newPassPanel.setMaximumSize(new Dimension(350, 30));
        newPassPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        newPassPanel.setBackground(Color.WHITE); // Match panel background

        JPasswordField newPasswordField = new JPasswordField(20);
        newPasswordField.setMaximumSize(new Dimension(320, 30)); // Make it a bit smaller to fit the button
        newPasswordField.setPreferredSize(new Dimension(320, 30));
        newPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        newPassPanel.add(newPasswordField);

        JButton toggleNewPassVisibilityButton = new JButton("<html>&#x1F441;</html>"); // Eye icon
        toggleNewPassVisibilityButton.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16)); // Font for emoji
        toggleNewPassVisibilityButton.setBorderPainted(false);
        toggleNewPassVisibilityButton.setContentAreaFilled(false);
        toggleNewPassVisibilityButton.setFocusPainted(false);
        toggleNewPassVisibilityButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleNewPassVisibilityButton.addActionListener(new ActionListener() {
            private boolean showPassword = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                showPassword = !showPassword;
                if (showPassword) {
                    newPasswordField.setEchoChar((char) 0); // Show password
                } else {
                    newPasswordField.setEchoChar('*'); // Hide password
                }
            }
        });
        newPassPanel.add(toggleNewPassVisibilityButton);
        panel.add(newPassPanel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Confirm New Password Label and Field
        JLabel confirmPasswordLabel = new JLabel("Confirm New Password");
        confirmPasswordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(confirmPasswordLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Panel for confirm password field and toggle button
        JPanel confirmPassPanel = new JPanel();
        confirmPassPanel.setLayout(new BoxLayout(confirmPassPanel, BoxLayout.X_AXIS));
        confirmPassPanel.setMaximumSize(new Dimension(350, 30));
        confirmPassPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPassPanel.setBackground(Color.WHITE); // Match panel background

        JPasswordField confirmPasswordField = new JPasswordField(20); // Use JPasswordField for password input
        confirmPasswordField.setMaximumSize(new Dimension(320, 30)); // Make it a bit smaller to fit the button
        confirmPasswordField.setPreferredSize(new Dimension(320, 30));
        confirmPasswordField.setAlignmentX(Component.CENTER_ALIGNMENT);
        confirmPassPanel.add(confirmPasswordField);

        JButton toggleConfirmPassVisibilityButton = new JButton("<html>&#x1F441;</html>"); // Eye icon
        toggleConfirmPassVisibilityButton.setFont(new Font("Segoe UI Symbol", Font.PLAIN, 16)); // Font for emoji
        toggleConfirmPassVisibilityButton.setBorderPainted(false);
        toggleConfirmPassVisibilityButton.setContentAreaFilled(false);
        toggleConfirmPassVisibilityButton.setFocusPainted(false);
        toggleConfirmPassVisibilityButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        toggleConfirmPassVisibilityButton.addActionListener(new ActionListener() {
            private boolean showPassword = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                showPassword = !showPassword;
                if (showPassword) {
                    confirmPasswordField.setEchoChar((char) 0); // Show password
                } else {
                    confirmPasswordField.setEchoChar('*'); // Hide password
                }
            }
        });
        confirmPassPanel.add(toggleConfirmPassVisibilityButton);
        panel.add(confirmPassPanel);


        panel.add(Box.createRigidArea(new Dimension(0, 30))); // More space before the button

        // Save New Password Button
        RoundedButton savePasswordButton = new RoundedButton("Save New Password", 20);
        savePasswordButton.setBackground(new Color(46, 204, 113));
        savePasswordButton.setForeground(Color.WHITE);
        savePasswordButton.setMaximumSize(new Dimension(350, 30));
        savePasswordButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        savePasswordButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        savePasswordButton.addActionListener(e -> {
            String newPass = new String(newPasswordField.getPassword());
            String confirmPass = new String(confirmPasswordField.getPassword());

            if (newPass.isEmpty() || confirmPass.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in both password fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // TODO: Implement actual password update logic here
                // Use ResetPassword.userEmail and the newPass to update the user's password in the database
                // Example: UserAuthenticator.updatePassword(ResetPassword.userEmail, newPass);
                boolean passwordUpdated = UserAuthenticator.updatePassword(ResetPassword.userEmail, newPass); // Assuming this method exists

                if (passwordUpdated) {
                    // Navigate to the new PasswordChangedScreen
                    frame.dispose();
                    PasswordChangedScreen.showPasswordChangedScreen();
                } else {
                    JOptionPane.showMessageDialog(frame, "Failed to update password. Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        savePasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                savePasswordButton.setBackground(new Color(39, 174, 96)); // Darker green on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                savePasswordButton.setBackground(new Color(46, 204, 113)); // Original green
            }
            @Override
            public void mousePressed(MouseEvent e) {
                savePasswordButton.setBackground(new Color(33, 148, 82)); // Even darker when pressed
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                savePasswordButton.setBackground(new Color(39, 174, 96)); // Back to hover color
            }
        });
        panel.add(savePasswordButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(panel, gbc);
        frame.setVisible(true);
    }
}

// Dummy classes for compilation (replace with your actual implementations)
// These are included to make the provided code runnable for demonstration.
// In a real project, these would be in their respective packages.


// Dummy RoundedTextField
class RoundedTextField extends JTextField {
    private Shape shape;
    private int radius;

    public RoundedTextField(int size) {
        super(size);
        this.radius = 15; // Default radius
        setOpaque(false); // As suggested by @camel. We make the component transparent.
        setPreferredSize(new Dimension(size * 10, 30)); // A reasonable default preferred size
    }

    public RoundedTextField(int size, int radius) {
        super(size);
        this.radius = radius;
        setOpaque(false);
        setPreferredSize(new Dimension(size * 10, 30));
    }

    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
    }

    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            // Fully qualify RoundRectangle2D
            shape = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        }
        return shape.contains(x, y);
    }
}

// Dummy RoundedButton
class RoundedButton extends JButton {
    private int radius;

    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setOpaque(false); // Make the button transparent
        setContentAreaFilled(false); // Don't paint the content area
        setBorderPainted(false); // Don't paint the border
        setFocusPainted(false); // Don't paint the focus border
    }

    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(getBackground().darker());
        } else {
            g.setColor(getBackground());
        }
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        super.paintComponent(g); // Paint the text and icon
    }

    protected void paintBorder(Graphics g) {
        // You can paint a custom border here if needed
        // g.setColor(getForeground());
        // g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
    }

    public boolean contains(int x, int y) {
        // Fully qualify RoundRectangle2D
        Shape shape = new java.awt.geom.RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
        return shape.contains(x, y);
    }
}



// Dummy UserAuthenticator for updating password


// New PasswordChangedScreen Class
class PasswordChangedScreen {
    public static void showPasswordChangedScreen() {
        JFrame frame = new JFrame("Password Changed");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new GridBagLayout());

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setPreferredSize(new Dimension(550, 500));
        panel.setMaximumSize(new Dimension(550, 500));

        // Back Button (Optional for this screen, depends on UX flow)
        // If the user is expected to go back to a login screen, this is useful.
        // If it's a final confirmation, it might just lead to Login.
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBackground(Color.WHITE);
        JButton backButton = new JButton("<html>&#x2190;</html>");
        backButton.setFont(new Font("Arial", Font.BOLD, 24));
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setFocusPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            frame.dispose();
            // Assuming Login.java exists externally for navigation back to login
            Login.main(null);
        });
        headerPanel.add(backButton);
        panel.add(headerPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));


        // Logo - Dynamically scaled from classpath
        int targetHeight = 30;
        ImageIcon logoIcon;
        java.net.URL logoImageUrl = PasswordChangedScreen.class.getClassLoader().getResource("whole_logo.png");
        if (logoImageUrl != null) {
            logoIcon = new ImageIcon(logoImageUrl);
            if (logoIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
                logoIcon = new ImageIcon(new BufferedImage(targetHeight, targetHeight, BufferedImage.TYPE_INT_ARGB)); // Transparent placeholder
            }
        } else {
            logoIcon = new ImageIcon(new BufferedImage(targetHeight, targetHeight, BufferedImage.TYPE_INT_ARGB)); // Transparent placeholder
        }

        int origWidth = logoIcon.getIconWidth();
        int origHeight = logoIcon.getIconHeight();
        int targetWidth = (origHeight > 0) ? (int) ((double) origWidth / origHeight * targetHeight) : targetHeight;
        Image scaledLogo = logoIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(logoLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        JLabel title = new JLabel("Payroll Management System");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        panel.add(Box.createRigidArea(new Dimension(0, 40))); // More space before the "Password Changed" text

        // "Password Changed" Text
        JLabel passwordChangedLabel = new JLabel("Password Changed");
        passwordChangedLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Larger, bold font
        passwordChangedLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(passwordChangedLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Checkmark Icon (Verified.png)
        JLabel checkmarkLabel;
        ImageIcon checkmarkIcon;
        java.net.URL checkmarkImageUrl = PasswordChangedScreen.class.getClassLoader().getResource("Verified.png");
        int checkmarkSize = 100; // Desired size for the checkmark icon

        if (checkmarkImageUrl != null) {
            checkmarkIcon = new ImageIcon(checkmarkImageUrl);
            // Scale the checkmark icon if needed
            Image scaledCheckmark = checkmarkIcon.getImage().getScaledInstance(checkmarkSize, checkmarkSize, Image.SCALE_SMOOTH);
            checkmarkLabel = new JLabel(new ImageIcon(scaledCheckmark));
        } else {
            // Fallback: If image not found, use a transparent placeholder
            checkmarkIcon = new ImageIcon(new BufferedImage(checkmarkSize, checkmarkSize, BufferedImage.TYPE_INT_ARGB));
            checkmarkLabel = new JLabel(checkmarkIcon);
        }
        checkmarkLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(checkmarkLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 40))); // Space before the continue button

        // Continue Button
        RoundedButton continueButton = new RoundedButton("Continue", 20);
        continueButton.setBackground(new Color(46, 204, 113));
        continueButton.setForeground(Color.WHITE);
        continueButton.setMaximumSize(new Dimension(350, 30));
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        continueButton.addActionListener(e -> {
            frame.dispose();
            // Assuming Login.java exists externally for navigation back to login
            Login.main(null);
        });
        continueButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                continueButton.setBackground(new Color(39, 174, 96)); // Darker green on hover
            }
            @Override
            public void mouseExited(MouseEvent e) {
                continueButton.setBackground(new Color(46, 204, 113)); // Original green
            }
            @Override
            public void mousePressed(MouseEvent e) {
                continueButton.setBackground(new Color(33, 148, 82)); // Even darker when pressed
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                continueButton.setBackground(new Color(39, 174, 96)); // Back to hover color
            }
        });
        panel.add(continueButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(panel, gbc);
        frame.setVisible(true);
    }
}
