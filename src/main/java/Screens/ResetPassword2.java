import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class ResetPassword2 {
    public static void showNewPasswordScreen() {
        JFrame frame = new JFrame("New Password");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 360);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setBackground(Color.LIGHT_GRAY);
        panel.setLayout(null);

        // Logo
        ImageIcon rawLogo = new ImageIcon("ae37f58d-efc0-47bc-95cd-19c058c6f708.png");
        Image scaledImage = rawLogo.getImage().getScaledInstance(300, 70, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
        logoLabel.setBounds(100, 20, 300, 70);
        panel.add(logoLabel);

        JLabel title = new JLabel("Payroll Management System");
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setBounds(140, 100, 250, 20);
        panel.add(title);

        JLabel newPassLabel = new JLabel("New Password");
        newPassLabel.setBounds(170, 130, 200, 15);
        panel.add(newPassLabel);

        RoundedPasswordField newPasswordField = new RoundedPasswordField(20);
        newPasswordField.setBounds(170, 150, 160, 35);
        newPasswordField.setBackground(Color.WHITE);
        newPasswordField.setForeground(Color.BLACK);
        panel.add(newPasswordField);

        JLabel confirmPassLabel = new JLabel("Confirm Password");
        confirmPassLabel.setBounds(170, 190, 200, 15);
        panel.add(confirmPassLabel);

        RoundedPasswordField confirmPasswordField = new RoundedPasswordField(20);
        confirmPasswordField.setBounds(170, 210, 160, 35);
        confirmPasswordField.setBackground(Color.WHITE);
        confirmPasswordField.setForeground(Color.BLACK);
        panel.add(confirmPasswordField);

        RoundedButton resetButton = new RoundedButton("Reset Password", 20);
        resetButton.setBackground(new Color(46, 204, 113));
        resetButton.setBounds(170, 260, 160, 35);

        resetButton.addActionListener(e -> {
            String newPass = String.valueOf(newPasswordField.getPassword());
            String confirmPass = String.valueOf(confirmPasswordField.getPassword());

            if (newPass.isEmpty() || confirmPass.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill in both fields.");
            } else if (!newPass.equals(confirmPass)) {
                JOptionPane.showMessageDialog(frame, "Passwords do not match.");
            } else {
                JOptionPane.showMessageDialog(frame, "Password has been reset!");
                frame.dispose();
            }
        });

        panel.add(resetButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
