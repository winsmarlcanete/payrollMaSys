package Screens;

import org.payroll.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class About extends JPanel {
    public About() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Logo and Title
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);

        int targetHeight = 50; // Original height from PayrollScreen snippet
        ImageIcon logoIcon = new ImageIcon(getClass().getClassLoader().getResource("whole_logo.png"));
        int origWidth = logoIcon.getIconWidth();
        int origHeight = logoIcon.getIconHeight();
        int targetWidth = (int) ((double) origWidth / origHeight * targetHeight);
        Image scaledLogo = logoIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogo);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        logoLabel.setBorder(new EmptyBorder(25, 0, 0, 0));
        logoPanel.add(logoLabel);

        // Description paragraph
        JTextArea description = new JTextArea(
                "The Payroll Management System is a comprehensive solution designed to streamline and automate payroll\n"
                        +
                        "processes for Synergy Grafix Corporation. Developed by a dedicated team of computer science students as\n"
                        +
                        "part of their final project for CS 301 - Software Engineering 1, this system aims to enhance efficiency,\n"
                        +
                        "accuracy, and transparency in payroll operations.");

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

        // Final paragraph
        JTextArea closing = new JTextArea(
                "By leveraging the power of technology, our Payroll Management System empowers Synergy Grafix\n" +
                        "Corporation to optimize its payroll processes and focus on core business activities.");

        // Developer section
        JTextArea devs = new JTextArea(
                "Developers:\n" +
                        "• Cañete, Winsmarl P.\n" +
                        "• Serrano, Jerwin Paul C.\n" +
                        "• Supan, Marc Laurence W.");

        styleTextArea(description);
        styleTextArea(features);
        styleTextArea(closing);
        styleTextArea(devs);

        int textAreaWidth = 780; // Reduced width
        description.setMaximumSize(new Dimension(textAreaWidth, Integer.MAX_VALUE));
        features.setMaximumSize(new Dimension(textAreaWidth, Integer.MAX_VALUE));
        closing.setMaximumSize(new Dimension(textAreaWidth, Integer.MAX_VALUE));
        devs.setMaximumSize(new Dimension(textAreaWidth, Integer.MAX_VALUE));

        // Add components to mainPanel
        mainPanel.add(description);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(features);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(closing);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(devs);
//        mainPanel.setBorder(BorderFactory.createLineBorder(MainWindow.activeColor, 10));

//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        gbc.weightx = 1.0;
//        gbc.weighty = 1.0;
//        gbc.fill = GridBagConstraints.NONE; // Don't stretch the mainPanel
//        gbc.anchor = GridBagConstraints.CENTER;

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setBackground(Color.WHITE);
        centerWrapper.add(mainPanel);

        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MainWindow.activeColor, 10),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        add(logoPanel, BorderLayout.NORTH);
        add(centerWrapper, BorderLayout.CENTER);
    }

    private void styleTextArea(JTextArea area) {
        area.setEditable(false);
        area.setFocusable(false);
        area.setBackground(Color.WHITE);
        area.setFont(new Font("SansSerif", Font.PLAIN, 14));
        area.setMargin(new Insets(5, 20, 5, 20));
        area.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Make the text area wrap words
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        // Set both preferred and maximum size
        int textAreaWidth = 780;
        area.setMaximumSize(new Dimension(textAreaWidth, Integer.MAX_VALUE));
        area.setPreferredSize(new Dimension(textAreaWidth, area.getPreferredSize().height));
    }
}