package com.cyclefix.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Handles the SQLite connection and first-time database setup.
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:cyclefix.db";

    private DatabaseConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /** Creates the users table and a default administrator if none exists. */
    public static void initialize() {
        String sql = """
                CREATE TABLE IF NOT EXISTS users (
                    id       INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT NOT NULL UNIQUE,
                    password TEXT NOT NULL,
                    name     TEXT NOT NULL,
                    roles    TEXT NOT NULL
                             CHECK (roles IN ('ADMINISTRATOR','REPAIR_STAFF','CASHIER')),
                    status   TEXT NOT NULL DEFAULT 'PENDING'
                             CHECK (status IN ('PENDING','ACTIVE','INACTIVE'))
                )
                """;
        try (Connection conn = getConnection(); Statement st = conn.createStatement()) {
            st.execute(sql);
        } catch (SQLException e) {
            System.out.println("Database setup failed: " + e.getMessage());
            System.exit(1);
        }
        new UserDao().seedDefaultAdmin();
    }
}
