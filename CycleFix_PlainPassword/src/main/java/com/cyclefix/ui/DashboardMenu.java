package com.cyclefix.ui;

import com.cyclefix.model.User;

import java.util.Scanner;

/**
 * Screen shown after a successful login. Returns to the main menu on logout.
 */
public class DashboardMenu {
    private final User user;
    private final Scanner scanner;

    public DashboardMenu(User user, Scanner scanner) {
        this.user = user;
        this.scanner = scanner;
    }

    public void show() {
        System.out.println("\nWelcome, " + user.name() + " (" + user.roles() + ")!");
        boolean loggedIn = true;
        while (loggedIn) {
            System.out.println("\n--- Dashboard ---");
            System.out.println("1. View my profile");
            System.out.println("2. Logout");
            System.out.print("Choose: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> {
                    System.out.println("ID       : " + user.id());
                    System.out.println("Username : " + user.username());
                    System.out.println("Name     : " + user.name());
                    System.out.println("Role     : " + user.roles());
                    System.out.println("Status   : " + user.status());
                }
                case "2" -> {
                    System.out.println("Logged out successfully.");
                    loggedIn = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
