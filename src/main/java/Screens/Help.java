package Screens;

import javax.swing.*;
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
        JLabel logoLabel = new JLabel("SynergyGrafixCorp.", JLabel.CENTER);
        logoLabel.setFont(new Font("Arial", Font.BOLD, 24));
        logoLabel.setForeground(new Color(0, 128, 0));
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

        contentPanel.add(guideLabel, BorderLayout.CENTER);

        // Add the developer contacts
        JPanel contactPanel = new JPanel();
        contactPanel.setLayout(new GridLayout(3, 1));
        contactPanel.setBackground(Color.WHITE);

        JLabel contact1 = new JLabel("wins@gmail.com", JLabel.CENTER);
        JLabel contact2 = new JLabel("jer@gmail.com", JLabel.CENTER);
        JLabel contact3 = new JLabel("ml@gmail.com", JLabel.CENTER);

        contactPanel.add(contact1);
        contactPanel.add(contact2);
        contactPanel.add(contact3);

        contentPanel.add(contactPanel, BorderLayout.SOUTH);

        // Add the content panel to this JPanel
        add(contentPanel, BorderLayout.CENTER);
    }
}