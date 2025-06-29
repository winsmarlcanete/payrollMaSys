package Screens;

import org.payroll.MainWindow;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Help extends JPanel {

    public Help() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Create the main content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Add the logo and title
        int targetHeight = 50; // Original height from PayrollScreen snippet
        // Load and scale the logo image
        ImageIcon logoIcon = new ImageIcon(getClass().getClassLoader().getResource("whole_logo.png"));
        int origWidth = logoIcon.getIconWidth();
        int origHeight = logoIcon.getIconHeight();
        int targetWidth = (int) ((double) origWidth / origHeight * targetHeight);
        Image scaledLogo = logoIcon.getImage().getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);

        ImageIcon scaledLogoIcon = new ImageIcon(scaledLogo);
        JLabel logoLabel = new JLabel(scaledLogoIcon);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        logoLabel.setBorder(new EmptyBorder(30, 0, 0, 0));

        contentPanel.add(logoLabel, BorderLayout.NORTH);

        // Add the instructional guide link
        JLabel guideLabel = new JLabel("<html><a href='#'>Instructional Guide</a></html>", JLabel.CENTER);
        guideLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        guideLabel.setForeground(Color.BLUE);
        guideLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add MouseListener to handle click event
        guideLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(Help.this, "Instructional Guide clicked.");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                guideLabel.setText("<html><a href='#' style='color: darkblue;'>Instructional Guide</a></html>");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                guideLabel.setText("<html><a href='#'>Instructional Guide</a></html>");
            }
        });

        // Add the developer contacts
//        JPanel contactPanel = new JPanel();
//        contactPanel.setLayout(new GridLayout(3, 1));
//        contactPanel.setBackground(Color.WHITE);

        JLabel contact1 = new JLabel("wins@gmail.com", JLabel.CENTER);
        JLabel contact2 = new JLabel("jer@gmail.com", JLabel.CENTER);
        JLabel contact3 = new JLabel("ml@gmail.com", JLabel.CENTER);

//        contactPanel.add(contact1);
//        contactPanel.add(contact2);
//        contactPanel.add(contact3);

        guideLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contact1.setAlignmentX(Component.CENTER_ALIGNMENT);
        contact2.setAlignmentX(Component.CENTER_ALIGNMENT);
        contact3.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(guideLabel);
        centerPanel.add(Box.createVerticalStrut(20)); // Add some space
        centerPanel.add(contact1);
        centerPanel.add(contact2);
        centerPanel.add(contact3);

        JPanel wrapperPanel = new JPanel(new GridBagLayout());
        wrapperPanel.setBackground(Color.WHITE);
        wrapperPanel.add(centerPanel);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(MainWindow.activeColor, 10),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        contentPanel.add(wrapperPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }
}