package com.cyclefix;

import com.cyclefix.dao.DatabaseConnection;
import com.cyclefix.ui.MainMenu;

/**
 * Application entry point for the CycleFix Repair and Maintenance System.
 */
public class Main {
    public static void main(String[] args) {
        DatabaseConnection.initialize();
        new MainMenu().show();
    }
}
