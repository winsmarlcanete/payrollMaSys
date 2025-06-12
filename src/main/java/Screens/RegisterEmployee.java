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

public class RegisterEmployee extends JPanel {

    private final JTextField firstNameField = new JTextField(15);
    private final JTextField middleNameField = new JTextField(15);
    private final JTextField lastNameField = new JTextField(15);
    private final JTextField employeeIdField = new JTextField(15);
    private final JTextField ratePerHourField = new JTextField(15);
    private final JTextField tinNoField = new JTextField(15);
    private final JTextField sssNoField = new JTextField(15);
    private final JTextField pagIbigNoField = new JTextField(15);
    private final JTextField philHealthNoField = new JTextField(15);

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
        row = addField(panel, gbc, row, "Department", departmentBox);
        row = addField(panel, gbc, row, "Employment Status", employmentStatusBox);
        row = addField(panel, gbc, row, "Rate / Hour (₱)", ratePerHourField);
        row = addField(panel, gbc, row, "TIN no.", tinNoField);
        row = addField(panel, gbc, row, "SSS no.", sssNoField);
        row = addField(panel, gbc, row, "Pag-Ibig no.", pagIbigNoField);
        row = addField(panel, gbc, row, "PhilHealth no.", philHealthNoField);
        row = addField(panel, gbc, row, "Shift start", shiftStartSpinner);
        row = addField(panel, gbc, row, "Shift end", shiftEndSpinner);



        // Register Button
        JButton registerButton = createRegisterButton();
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(registerButton, gbc);

        // === Create image label ===
        JLabel imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(350, 400));
        imageLabel.setMinimumSize(new Dimension(350, 400));
        imageLabel.setMaximumSize(new Dimension(350, 400));
        imageLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);

// === Create fingerprint button ===
        JButton scanFingerprintButton = new JButton("Scan Fingerprint");
        scanFingerprintButton.setFocusPainted(false);
        scanFingerprintButton.setBackground(new Color(0, 102, 204));
        scanFingerprintButton.setForeground(Color.WHITE);
        scanFingerprintButton.setFont(new Font("Arial", Font.BOLD, 14));
        scanFingerprintButton.setPreferredSize(new Dimension(200, 40));

        JLabel statusLabel = new JLabel(" ");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(Color.DARK_GRAY);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label

// You can add an ActionListener here to trigger fingerprint scanning
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


// === Group image and button into a vertical panel ===
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
        imagePanel.setBackground(Color.WHITE);
        imagePanel.add(imageLabel);
        imagePanel.add(Box.createVerticalStrut(10));
        imagePanel.add(scanFingerprintButton);
        imagePanel.add(Box.createVerticalStrut(5));
        imagePanel.add(statusLabel);


// === Add the image panel to the main panel (right side) ===
        GridBagConstraints imageGbc = new GridBagConstraints();
        imageGbc.gridx = 2; // Rightmost column
        imageGbc.gridy = 0; // Align to top
        imageGbc.gridheight = row; // Span height of the form
        imageGbc.insets = new Insets(10, 20, 10, 10);
        imageGbc.fill = GridBagConstraints.NONE;
        imageGbc.anchor = GridBagConstraints.CENTER;

        panel.add(imagePanel, imageGbc);


        return panel;
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
                String firstName = firstNameField.getText().trim();
                String middleName = middleNameField.getText().trim();
                String lastName = lastNameField.getText().trim();
                String department = (String) departmentBox.getSelectedItem();
                String employmentStatus = (String) employmentStatusBox.getSelectedItem();
                BigDecimal ratePerHour = new BigDecimal(ratePerHourField.getText().trim());
                String tin = tinNoField.getText().trim();
                String philhealth = philHealthNoField.getText().trim();
                String pagibig = pagIbigNoField.getText().trim();
                String sss = sssNoField.getText().trim();
                java.util.Date shiftStartUtilDate = (java.util.Date) shiftStartSpinner.getValue();
                java.util.Date shiftEndUtilDate = (java.util.Date) shiftEndSpinner.getValue();


                Time shiftStart = new Time(shiftStartUtilDate.getTime());
                Time shiftEnd = new Time(shiftEndUtilDate.getTime());

                Employee emp = new Employee(firstName, lastName, middleName, department,
                        employmentStatus, ratePerHour, tin, philhealth, pagibig, sss, shiftStart, shiftEnd, enrolled);

                System.out.println("Shift Start: " + shiftStart);
                System.out.println("Shift End: " + shiftEnd);


                zkFinger.close();

                EmployeeRegistration.registerEmployee(emp);
                } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter a valid numeric value for Rate per Hour.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        return button;
    }



}