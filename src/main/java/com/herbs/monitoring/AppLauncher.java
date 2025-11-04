package com.herbs.monitoring;

import com.herbs.monitoring.dao.DBUtil;
import com.herbs.monitoring.ui.LoginFrame;

import javax.swing.SwingUtilities;

/**
 * Application entry point that bootstraps the database and launches the Swing UI.
 */
public final class AppLauncher {
    private AppLauncher() {
    }

    public static void main(String[] args) {
        DBUtil.initializeDatabase();
        SwingUtilities.invokeLater(() -> {
            LoginFrame frame = new LoginFrame();
            frame.setVisible(true);
        });
    }
}
