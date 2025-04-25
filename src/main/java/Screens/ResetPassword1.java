
// ResetPassword1.java
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ResetPassword1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ResetPassword1().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Reset Password");
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

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setBounds(170, 130, 200, 15);
        panel.add(emailLabel);

        RoundedTextField emailField = new RoundedTextField(20);
        emailField.setBounds(170, 150, 160, 35);
        panel.add(emailField);

        RoundedButton sendButton = new RoundedButton("Send Reset Code", 20);
        sendButton.setBackground(new Color(46, 204, 113));
        sendButton.setBounds(170, 200, 160, 35);
        sendButton.addActionListener(e -> {
            String email = emailField.getText();
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter your email.");
            } else {
                frame.dispose();
                ResetPassword2.showNewPasswordScreen();
            }
        });
        panel.add(sendButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}

class ResetPassword2 {
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
        panel.add(newPasswordField);

        JLabel confirmPassLabel = new JLabel("Confirm Password");
        confirmPassLabel.setBounds(170, 190, 200, 15);
        panel.add(confirmPassLabel);

        RoundedPasswordField confirmPasswordField = new RoundedPasswordField(20);
        confirmPasswordField.setBounds(170, 210, 160, 35);
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

class RoundedTextField extends JTextField {
    private int radius;

    public RoundedTextField(int radius) {
        this.radius = radius;
        setOpaque(false);
        setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}

class RoundedPasswordField extends JPasswordField {
    private int radius;

    public RoundedPasswordField(int radius) {
        this.radius = radius;
        setOpaque(false);
        setBorder(new EmptyBorder(5, 10, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}

class RoundedButton extends JButton {
    private int radius;

    public RoundedButton(String text, int radius) {
        super(text);
        this.radius = radius;
        setContentAreaFilled(false);
        setFocusPainted(false);
        setForeground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        super.paintComponent(g2);
        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius);
        g2.dispose();
    }
}
