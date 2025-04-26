package Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import Components.MenuPanel;

public class Help extends JFrame {

    public Help() {
        // Set up the frame
        setTitle("Help");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Maximize the frame on launch
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            }
        });

        // Add the menu panel for navigation
        MenuPanel tabPanel = new MenuPanel(this); // Use 'this' to refer to the current Help frame
        add(tabPanel, BorderLayout.NORTH);

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
        guideLabel.setForeground(Color.BLUE);  // Make it visually clear it's a link
        guideLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Show a hand cursor on hover

        // Add MouseListener to handle click event
        guideLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle the click event (for now, just show a message)
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

        // Add the content panel to the frame
        add(contentPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Help helpFrame = new Help();
            helpFrame.setVisible(true);
        });
    }
}
