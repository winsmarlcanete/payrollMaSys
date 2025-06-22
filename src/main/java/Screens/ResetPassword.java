package Screens;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import Components.RoundedTextField; // Assuming this class exists
import Components.RoundedButton;     // Assuming this class exists


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage; // Added this import just in case for logo fallback

import Algorithms.OTPGenerator; // Assuming this class exists
import Config.EmailService;   // Assuming this class exists
import Module.Security.ForgotPassword; // Assuming this class exists, used for saveOtp

// Assuming Login.java is in the Screens package

public class ResetPassword {

    public static String userEmail; // To carry the email across screens

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ResetPassword().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Reset Password");
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
            Login.main(null); // Directs to Login.java by calling its main method
        });
        headerPanel.add(backButton);
        panel.add(headerPanel);

        // Add rigid area after header panel for spacing from logo
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Logo - Dynamically scaled from classpath
        int targetHeight = 30; // Consistent height for logos across screens
        ImageIcon logoIcon = new ImageIcon(getClass().getClassLoader().getResource("whole_logo.png"));
        // Fallback for logo if not found
        if (logoIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Warning: Logo image 'whole_logo.png' not found or could not be loaded in ResetPassword.");
            logoIcon = new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)); // Transparent placeholder
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
        sendCodeButton.addActionListener(e -> {
            String email = emailField.getText();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your email.", "Error", JOptionPane.ERROR_MESSAGE);
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
        answerSecurityQuestionButton.addActionListener(e -> {
            String email = emailField.getText();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your email to answer security question.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                userEmail = email; // Store email for the next screen
                frame.dispose(); // Close current frame
                SecurityQuestionScreen.showSecurityQuestionScreen(); // Open the new Security Question screen
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
        ImageIcon logoIcon = new ImageIcon(EnterCodeScreen.class.getClassLoader().getResource("whole_logo.png"));
        // Fallback for logo if not found
        if (logoIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Warning: Logo image 'whole_logo.png' not found or could not be loaded in EnterCodeScreen.");
            logoIcon = new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)); // Transparent placeholder
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
        verifyButton.addActionListener(e -> {
            String enteredCode = codeField.getText();
            if (enteredCode.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter the code.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {

                if (ForgotPassword.verifyOtp(ResetPassword.userEmail, enteredCode)) {
                    // If verification is successful
                    JOptionPane.showMessageDialog(frame, "Code verified successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    frame.dispose(); // Close current frame
                    //NewPasswordScreen.showNewPasswordScreen(); // Open the new New Password screen
                } else {
                    // If verification fails
                    JOptionPane.showMessageDialog(frame, "Invalid code. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                frame.dispose();
                // NewPasswordScreen.showNewPasswordScreen();
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
        ImageIcon logoIcon = new ImageIcon(SecurityQuestionScreen.class.getClassLoader().getResource("whole_logo.png"));
        // Fallback for logo if not found
        if (logoIcon.getImageLoadStatus() != MediaTracker.COMPLETE) {
            System.err.println("Warning: Logo image 'whole_logo.png' not found or could not be loaded in SecurityQuestionScreen.");
            logoIcon = new ImageIcon(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)); // Transparent placeholder
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

        // Security Question Label
        JLabel questionLabel = new JLabel("What is the name of your favorite pet?");
        questionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16)); // Slightly larger font for question
        panel.add(questionLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Answer Field
        RoundedTextField answerField = new RoundedTextField(20);
        answerField.setMaximumSize(new Dimension(350, 30));
        answerField.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(answerField);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        RoundedButton submitAnswerButton = new RoundedButton("Submit Answer", 20);
        submitAnswerButton.setBackground(new Color(46, 204, 113)); // Matched green color
        submitAnswerButton.setForeground(Color.WHITE);
        submitAnswerButton.setMaximumSize(new Dimension(350, 30));
        submitAnswerButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitAnswerButton.addActionListener(e -> {
            String enteredAnswer = answerField.getText();
            if (enteredAnswer.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your answer.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // TODO: Implement actual security question answer verification logic here
                JOptionPane.showMessageDialog(frame, "Answer entered: " + enteredAnswer + " for email: " + ResetPassword.userEmail, "Verification Status (TODO)", JOptionPane.INFORMATION_MESSAGE);
                // If verification is successful:
                // frame.dispose();
                // NewPasswordScreen.showNewPasswordScreen(); // A new screen for setting new password
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