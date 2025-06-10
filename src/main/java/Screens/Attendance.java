package Screens;

import Config.ZkFinger;

import javax.swing.*;
import java.awt.*;

public class Attendance extends javax.swing.JPanel {

    public Attendance() {
        initComponents();
    }
    private final ZkFinger zkFinger = new ZkFinger();
    private final JLabel statusLabel = new JLabel("Initializing...", SwingConstants.CENTER);

    private final JButton openDevice = new JButton("Open Device");
    private final JButton enrollButton = new JButton("Enroll Fingerprint");



    private void initComponents() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 10)); // Main layout

        // Title at the top
        JLabel titleLabel = new JLabel("Attendance Management", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Center area: Text field + image + status
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        JTextField textField = new JTextField(15);
        centerPanel.add(textField, BorderLayout.NORTH);

        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(800, 800)); // Adjust as needed
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(imageLabel, BorderLayout.CENTER);

        centerPanel.add(statusLabel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom area: Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        openDevice.setPreferredSize(new Dimension(150, 30));
        enrollButton.setPreferredSize(new Dimension(170, 30));

        buttonPanel.add(openDevice);
        buttonPanel.add(enrollButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button logic
        openDevice.addActionListener(e -> {
            boolean ready = zkFinger.init();
            statusLabel.setText(ready ? "Device ready" : "Failed to initialize device.");
        });

        enrollButton.addActionListener(e -> {
            try {
                int value = Integer.parseInt(textField.getText().trim());
                ZkFinger.FingerprintTemplate enrolled = zkFinger.enrollFingerprint(imageLabel);
                if (enrolled != null) {
                    boolean success = zkFinger.saveTemplateToDatabase(value, enrolled);
                    statusLabel.setText(success ? "Fingerprint saved!" : "Save failed.");
                } else {
                    JOptionPane.showMessageDialog(null, "Enrollment failed. Please try again.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid employee ID.");
            }
        });
    }}

