package com.herbs.monitoring;

import javax.swing.SwingUtilities;
import com.herbs.monitoring.dao.DBUtil;
import com.herbs.monitoring.ui.LoginFrame;

public class AppLauncher {
    public static void main(String[] args) {
        // Initialize the database
        DBUtil.initializeDatabase();

        // Start the Swing user interface
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}