package com.cyclefix.ui;

import com.cyclefix.dao.UserDao;
import com.cyclefix.model.User;

import java.util.Optional;
import java.util.Scanner;

/**
 * Main menu: Register, Login, Exit.
 */
public class MainMenu {
    private static final String[] ROLES = {"ADMINISTRATOR", "REPAIR_STAFF", "CASHIER"};

    private final Scanner scanner = new Scanner(System.in);
    private final UserDao userDao = new UserDao();

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n==================================");
            System.out.println(" CycleFix Repair & Maintenance System");
            System.out.println("==================================");
            System.out.println("1. Register Account");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> register();
                case "2" -> login();
                case "3" -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Please enter 1-3.");
            }
        }
    }

    private void register() {
        System.out.println("\n--- Register Account ---");
        String name = prompt("Full name: ");
        String username = prompt("Username: ");
        if (name.isEmpty() || username.isEmpty()) {
            System.out.println("Name and username cannot be empty.");
            return;
        }
        if (userDao.usernameExists(username)) {
            System.out.println("Username already taken.");
            return;
        }
        String password = prompt("Password: ");
        if (password.length() < 4) {
            System.out.println("Password must be at least 4 characters.");
            return;
        }

        System.out.println("Select role:");
        for (int i = 0; i < ROLES.length; i++) {
            System.out.println((i + 1) + ". " + ROLES[i]);
        }
        String choice = prompt("Role: ");
        int index;
        try {
            index = Integer.parseInt(choice) - 1;
        } catch (NumberFormatException e) {
            index = -1;
        }
        if (index < 0 || index >= ROLES.length) {
            System.out.println("Invalid role.");
            return;
        }

        if (userDao.register(username, password, name, ROLES[index])) {
            System.out.println("Registered! Your account is PENDING until an administrator approves it.");
        }
    }

    private void login() {
        System.out.println("\n--- Login ---");
        String username = prompt("Username: ");
        String password = prompt("Password: ");

        Optional<User> result = userDao.login(username, password);
        if (result.isEmpty()) {
            System.out.println("Invalid username or password.");
            return;
        }
        User user = result.get();
        switch (user.status()) {
            case "PENDING" -> System.out.println("Your account is still PENDING approval.");
            case "INACTIVE" -> System.out.println("Your account has been deactivated.");
            default -> new DashboardMenu(user, scanner).show();
        }
    }

    private String prompt(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }
}
