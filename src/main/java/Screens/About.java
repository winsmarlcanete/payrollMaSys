package Screens;

import javax.swing.*;
import java.awt.*;

public class About extends JPanel {
    public About() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 153, 0), 8)); // green border
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Logo and Title
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon(getClass().getClassLoader().getResource("whole_logo.png"));

        Image scaledLogo = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(scaledLogo));

        JLabel titleLabel = new JLabel();
        titleLabel.setText("<html><span style='color:#009900;font-weight:bold;'>Synergy</span><i>GrafixCorp.</i></html>");
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));

        logoPanel.add(logoLabel);
        logoPanel.add(Box.createRigidArea(new Dimension(10, 0)));
        logoPanel.add(titleLabel);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(logoPanel);
        mainPanel.add(Box.createVerticalStrut(10));

        // "About" Title
        JLabel aboutTitle = new JLabel("About");
        aboutTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
        aboutTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(aboutTitle);
        mainPanel.add(Box.createVerticalStrut(10));

        // Description paragraph
        JTextArea description = new JTextArea(
                "The Payroll Management System is a comprehensive solution designed to streamline and automate payroll\n"
                        +
                        "processes for Synergy Grafix Corporation. Developed by a dedicated team of computer science students as\n"
                        +
                        "part of their final project for CS 301 - Software Engineering 1, this system aims to enhance efficiency,\n"
                        +
                        "accuracy, and transparency in payroll operations.");
        styleTextArea(description);
        mainPanel.add(description);

        // Key Features
        JTextArea features = new JTextArea(
                "Key Features:\n" +
                        "• Employee Management:\n" +
                        "   • Efficiently manage employee information, including personal details, contact information, and employment history.\n"
                        +
                        "• Time and Attendance Tracking:\n" +
                        "   • Accurately record employee attendance and time-off requests, ensuring precise payroll calculations.\n"
                        +
                        "• Payroll Processing:\n" +
                        "   • Automate payroll calculations, including taxes, deductions, and net pay.\n" +
                        "• Report Generation:\n" +
                        "   • Generate detailed payroll reports, including pay slips, tax summaries, and other essential documents.\n"
                        +
                        "• User-Friendly Interface:\n" +
                        "   • Intuitive and user-friendly design for easy navigation and data entry.");
        styleTextArea(features);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(features);

        // Final paragraph
        JTextArea closing = new JTextArea(
                "By leveraging the power of technology, our Payroll Management System empowers Synergy Grafix\n" +
                        "Corporation to optimize its payroll processes and focus on core business activities.");
        styleTextArea(closing);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(closing);

        // Developer section
        JTextArea devs = new JTextArea(
                "Developers:\n" +
                        "• Cañete, Winsmarl P.\n" +
                        "• Serrano, Jerwin Paul C.\n" +
                        "• Supan, Marc Laurence W.");
        styleTextArea(devs);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(devs);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void styleTextArea(JTextArea area) {
        area.setEditable(false);
        area.setFocusable(false);
        area.setBackground(Color.WHITE);
        area.setFont(new Font("SansSerif", Font.PLAIN, 14));
        area.setMargin(new Insets(5, 20, 5, 20));
    }
}