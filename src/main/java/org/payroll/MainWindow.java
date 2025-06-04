package org.payroll;

import javax.swing.*;

import Entity.Report;
import Screens.*;

public class MainWindow extends JFrame {
    public MainWindow() {
        setTitle("Payroll System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(rootPane);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JTabbedPane tabbedPane = new JTabbedPane();

        Employees employees = new Employees(this);
        RegisterEmployee regemployee = new RegisterEmployee();
        Attendance attendance = new Attendance();
        LeaveManagement leavemanagement = new LeaveManagement();
        Payroll payroll = new Payroll();
        Reports reports = new Reports();
        Help help = new Help();
        About about = new About();
        
        tabbedPane.addTab("Employees", employees);
        tabbedPane.addTab("Register Employee", regemployee);
        tabbedPane.addTab("Attendance", attendance);
        tabbedPane.addTab("Leave Management", leavemanagement);
        tabbedPane.addTab("Payroll", payroll);
        tabbedPane.addTab("Reports", reports);
        tabbedPane.addTab("Help", help);
        tabbedPane.addTab("About", about);


        add(tabbedPane);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }
}
