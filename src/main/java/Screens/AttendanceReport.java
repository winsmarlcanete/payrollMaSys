package Screens;

import javax.swing.*;
import java.awt.*;

public class AttendanceReport extends JPanel {
    public AttendanceReport() {
        JLabel label = new JLabel("Absent ka nanaman!");
        label.setFont(new Font("Arial", Font.BOLD, 50));
        this.add(label);
    }
}