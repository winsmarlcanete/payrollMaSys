package Screens;

public class Reports extends javax.swing.JPanel {

    public Reports() {
        initComponents();
    }

    private void initComponents() {
        // Initialize components here
        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new java.awt.BorderLayout());

        // Add components to the panel
        javax.swing.JLabel titleLabel = new javax.swing.JLabel("Reports Management");
        titleLabel.setFont(new java.awt.Font("Arial", 1, 24));
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(titleLabel, java.awt.BorderLayout.NORTH);

        // Additional components can be added here
    }
    
}
