package com.bitzh.hospitalsystem;

import com.bitzh.hospitalsystem.view.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true)
        });
    }
}