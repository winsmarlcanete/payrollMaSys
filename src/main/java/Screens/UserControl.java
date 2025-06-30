package Screens;

import javax.swing.*;
import java.awt.*;

public class UserControl extends JPanel {
    public UserControl() {
        setLayout(new BorderLayout());
        // Add placeholder content
        JLabel label = new JLabel("User Control Panel", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        add(label, BorderLayout.CENTER);
    }
}