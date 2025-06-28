package Screens;
import Config.ZkFinger;
import Entity.Employee;
import com.zkteco.biometric.FingerprintSensorEx;
import Module.Registration.EmployeeRegistration.EmployeeRegistration;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import Screens.Employees;

public class RegisterEmployee extends JPanel {

    private final JTextField firstNameField = new JTextField(15);
    private final JTextField middleNameField = new JTextField(15);
    private final JTextField lastNameField = new JTextField(15);
    private final JTextField employeeIdField = new JTextField(15);
    private final JTextField rateField = new JTextField(15);
    private final JTextField tinNoField = new JTextField(15);
    private final JTextField sssNoField = new JTextField(15);
    private final JTextField pagIbigNoField = new JTextField(15);
    private final JTextField philHealthNoField = new JTextField(15);

    private final JTextField pagIbigPercentageField = new JTextField(15);
    private final JTextField philHealthPercentageField = new JTextField(15);
    private final JTextField sssPercentageField = new JTextField(15);
    private final JTextField efundAmountField = new JTextField(15);
    private final JTextField otherDeductionsField = new JTextField(15);

    private final JTextField salaryAdjPercentageField = new JTextField(15);
    private final JTextField allowanceAmountField = new JTextField(15);
    private final JTextField otherCompAmountField = new JTextField(15);

    private final JSpinner shiftStartSpinner = new JSpinner(new SpinnerDateModel());
    private final JSpinner shiftEndSpinner = new JSpinner(new SpinnerDateModel());

    {
        shiftStartSpinner.setEditor(new JSpinner.DateEditor(shiftStartSpinner, "HH:mm:ss"));
        shiftEndSpinner.setEditor(new JSpinner.DateEditor(shiftEndSpinner, "HH:mm:ss"));
    }


    private final JComboBox<String> departmentBox = new JComboBox<>(
            new String[] { "Human Resource", "Accounting", "Sales", "Production (Pre-Press)",
                    "Production (Press)", "Production (Post-Press)", "Production (Quality Control)" });
    private final JComboBox<String> employmentStatusBox = new JComboBox<>(
            new String[] { "Regular", "Part-time", "Contract" });

    private final ZkFinger zkFinger = new ZkFinger();

    private ZkFinger.FingerprintTemplate enrolled = null;




    public RegisterEmployee() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createContentPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);

        // First Panel
        JPanel firstPanel = new JPanel(new GridBagLayout());
        firstPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        row = addField(firstPanel, gbc, row, "First Name", firstNameField);
        row = addField(firstPanel, gbc, row, "Middle Name", middleNameField);
        row = addField(firstPanel, gbc, row, "Last Name", lastNameField);
        row = addField(firstPanel, gbc, row, "Department", departmentBox);
        row = addField(firstPanel, gbc, row, "Employment Status", employmentStatusBox);
        row = addField(firstPanel, gbc, row, "Rate", rateField);
        row = addField(firstPanel, gbc, row, "Shift start", shiftStartSpinner);
        row = addField(firstPanel, gbc, row, "Shift end", shiftEndSpinner);

        // Second Panel
        JPanel secondPanel = new JPanel(new GridBagLayout());
        secondPanel.setBackground(Color.WHITE);
        row = 0; // Reset row for second panel
        row = addField(secondPanel, gbc, row, "TIN no.", tinNoField);
        row = addField(secondPanel, gbc, row, "SSS no.", sssNoField);
        row = addField(secondPanel, gbc, row, "Pag-Ibig no.", pagIbigNoField);
        row = addField(secondPanel, gbc, row, "Pag-Ibig Percentage (%)", pagIbigPercentageField);
        row = addField(secondPanel, gbc, row, "PhilHealth no.", philHealthNoField);
        row = addField(secondPanel, gbc, row, "PhilHealth Percentage (%)", philHealthPercentageField);
        row = addField(secondPanel, gbc, row, "SSS Percentage (%)", sssPercentageField);
        row = addField(secondPanel, gbc, row, "E-Fund Amount (₱)", efundAmountField);
        row = addField(secondPanel, gbc, row, "Other Deductions (₱)", otherDeductionsField);
        row = addField(secondPanel, gbc, row, "Salary Adjustment Percentage (%)", salaryAdjPercentageField);
        row = addField(secondPanel, gbc, row, "Allowance Amount (₱)", allowanceAmountField);
        row = addField(secondPanel, gbc, row, "Other Compensation Amount (₱)", otherCompAmountField);

        // Third Panel (Fingerprint Image)
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(350, 400));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton scanFingerprintButton = new JButton("Scan Fingerprint");
        scanFingerprintButton.setFocusPainted(false);
        scanFingerprintButton.setBackground(new Color(0, 102, 204));
        scanFingerprintButton.setForeground(Color.WHITE);
        scanFingerprintButton.setFont(new Font("Arial", Font.BOLD, 14));
        scanFingerprintButton.setPreferredSize(new Dimension(200, 40));

        JLabel statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.DARK_GRAY);

        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(new BoxLayout(thirdPanel, BoxLayout.Y_AXIS));
        thirdPanel.setBackground(Color.WHITE);
        thirdPanel.add(imageLabel);
        thirdPanel.add(Box.createVerticalStrut(50));
        thirdPanel.add(scanFingerprintButton);
        thirdPanel.add(statusLabel);

        // Add panels to the main panel
        mainPanel.add(firstPanel, BorderLayout.WEST);
        mainPanel.add(secondPanel, BorderLayout.CENTER);
        mainPanel.add(thirdPanel, BorderLayout.EAST);

        // Add Register Button
        JButton registerButton = createRegisterButton();
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(registerButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        scanFingerprintButton.addActionListener(e -> {
            new Thread(() -> {
                zkFinger.init();
                enrolled = zkFinger.enrollFingerprint(imageLabel);

                SwingUtilities.invokeLater(() -> {
                    if (enrolled != null) {
                        statusLabel.setText("✅ Fingerprint registered!");
                    } else {
                        statusLabel.setText("❌ Fingerprint registration failed.");
                    }
                });
            }).start();
        });

        return mainPanel;
    }

    private int addField(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        component.setFont(new Font("Arial", Font.PLAIN, 16));
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

        button.addActionListener(e -> {
            try {
                // Validate required fields
                if (firstNameField.getText().trim().isEmpty() ||
                        lastNameField.getText().trim().isEmpty() ||
                        departmentBox.getSelectedItem() == null ||
                        employmentStatusBox.getSelectedItem() == null ||
                        rateField.getText().trim().isEmpty() ||
                        tinNoField.getText().trim().isEmpty() ||
                        sssNoField.getText().trim().isEmpty() ||
                        pagIbigNoField.getText().trim().isEmpty() ||
                        philHealthNoField.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(this,
                            "Please fill in all required fields.",
                            "Missing Fields",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Parse and debug values
                String firstName = firstNameField.getText().trim();
                String middleName = middleNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String department = (String) departmentBox.getSelectedItem();
                String employmentStatus = (String) employmentStatusBox.getSelectedItem();
                BigDecimal rate = new BigDecimal(rateField.getText().trim());
                String tin = tinNoField.getText().trim();
                String philhealth = philHealthNoField.getText().trim();
                BigDecimal philhealthPercentage = new BigDecimal(philHealthPercentageField.getText().trim());
                String pagibig = pagIbigNoField.getText().trim();
                BigDecimal pagibigPercentage = new BigDecimal(pagIbigPercentageField.getText().trim());
                String sss = sssNoField.getText().trim();
                BigDecimal sssPercentage = new BigDecimal(sssPercentageField.getText().trim());
                BigDecimal efundAmount = new BigDecimal(efundAmountField.getText().trim());
                BigDecimal otherDeductions = new BigDecimal(otherDeductionsField.getText().trim());
                BigDecimal salaryAdjPercentage = new BigDecimal(salaryAdjPercentageField.getText().trim());
                BigDecimal allowanceAmount = new BigDecimal(allowanceAmountField.getText().trim());
                BigDecimal otherCompAmount = new BigDecimal(otherCompAmountField.getText().trim());
                java.util.Date shiftStartUtilDate = (java.util.Date) shiftStartSpinner.getValue();
                java.util.Date shiftEndUtilDate = (java.util.Date) shiftEndSpinner.getValue();

                Time shiftStart = new Time(shiftStartUtilDate.getTime());
                Time shiftEnd = new Time(shiftEndUtilDate.getTime());

                // Check if fingerprint is enrolled
                if (enrolled == null) {
                    JOptionPane.showMessageDialog(this,
                            "Fingerprint is not registered. Please scan the fingerprint.",
                            "Missing Fingerprint",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Create Employee object with fingerprint
                Employee emp = new Employee(firstName, lastName, middleName, department,
                        employmentStatus, rate, tin, philhealth, philhealthPercentage, pagibig, pagibigPercentage,
                        sss, sssPercentage, efundAmount, otherDeductions, salaryAdjPercentage, allowanceAmount,
                        otherCompAmount, shiftStart, shiftEnd, enrolled);

                EmployeeRegistration.registerEmployee(emp);

                JOptionPane.showMessageDialog(this,
                        "Employee registered successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid numeric values for percentages and amounts.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "An error occurred during registration. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println("Error during registration: " + ex.getMessage());
            }
        });

        return button;
    }



}