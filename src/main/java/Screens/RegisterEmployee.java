package Screens;

import Components.MenuPanel;
import javax.swing.*;
import java.awt.*;

public class RegisterEmployee extends JFrame {

    private final JTextField firstNameField = new JTextField(15);
    private final JTextField middleNameField = new JTextField(15);
    private final JTextField lastNameField = new JTextField(15);
    private final JTextField employeeIdField = new JTextField(15);
    private final JTextField ratePerHourField = new JTextField(15);
    private final JTextField tinNoField = new JTextField(15);
    private final JTextField sssNoField = new JTextField(15);
    private final JTextField pagIbigNoField = new JTextField(15);
    private final JTextField philHealthNoField = new JTextField(15);
    private final JComboBox<String> departmentBox = new JComboBox<>(
            new String[] { "Human Resource", "Accounting", "Sales", "Production (Pre-Press)",
                    "Production (Press)", "Production (Post-Press)", "Production (Quality Control)" });
    private final JComboBox<String> employmentStatusBox = new JComboBox<>(
            new String[] { "Regular", "Part-time", "Contract" });

    public RegisterEmployee() {
        setTitle("Register Employee");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Add MenuPanel to the top of the frame
        MenuPanel menuPanel = new MenuPanel(this);
        add(menuPanel, BorderLayout.NORTH);

        add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createContentPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        row = addField(panel, gbc, row, "First Name", firstNameField);
        row = addField(panel, gbc, row, "Middle Name", middleNameField);
        row = addField(panel, gbc, row, "Last Name", lastNameField);
        row = addField(panel, gbc, row, "Employee ID", employeeIdField);
        row = addField(panel, gbc, row, "Department", departmentBox);
        row = addField(panel, gbc, row, "Employment Status", employmentStatusBox);
        row = addField(panel, gbc, row, "Rate / Hour (₱)", ratePerHourField);
        row = addField(panel, gbc, row, "TIN no.", tinNoField);
        row = addField(panel, gbc, row, "SSS no.", sssNoField);
        row = addField(panel, gbc, row, "Pag-Ibig no.", pagIbigNoField);
        row = addField(panel, gbc, row, "PhilHealth no.", philHealthNoField);

        // Register Button
        JButton registerButton = createRegisterButton();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

        return panel;
    }

    private int addField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(component, gbc);

        return row + 1;
    }

    private JButton createRegisterButton() {
        JButton button = new JButton("Register");
        button.setFocusPainted(false);
        button.setBackground(new Color(0, 153, 0));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setPreferredSize(new Dimension(150, 40));
        button.addActionListener(e -> JOptionPane.showMessageDialog(
                RegisterEmployee.this,
                "Employee Registered Successfully!",
                "Success",
                JOptionPane.INFORMATION_MESSAGE));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterEmployee().setVisible(true));
    }
}
