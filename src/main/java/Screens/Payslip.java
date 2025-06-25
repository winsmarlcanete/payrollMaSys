package Screens;

import javax.swing.*;
import java.awt.*;

public class Payslip extends JPanel {
    public Payslip() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Create a label for the title
        JLabel titleLabel = new JLabel("Payslip");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Placeholder for payslip content
        JTextArea payslipContent = new JTextArea("Payslip details will be displayed here.");
        payslipContent.setFont(new Font("Arial", Font.PLAIN, 16));
        payslipContent.setEditable(false);
        payslipContent.setLineWrap(true);
        payslipContent.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(payslipContent);
        add(scrollPane, BorderLayout.CENTER);
    }
}
