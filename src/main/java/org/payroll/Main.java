package org.payroll;

import Screens.Login;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Login loginScreen = new Login();
            loginScreen.setVisible(true);
            loginScreen.setExtendedState(JFrame.MAXIMIZED_BOTH); // Optional: start maximized
        });
    }
}