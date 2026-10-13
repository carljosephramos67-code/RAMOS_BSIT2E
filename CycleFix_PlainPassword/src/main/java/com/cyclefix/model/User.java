package com.cyclefix.model;

/**
 * Immutable data model for a system user.
 *
 * @param id       primary key (user_id)
 * @param username unique login name
 * @param password plain-text password
 * @param name     full name of the user
 * @param roles    ADMINISTRATOR, REPAIR_STAFF, or CASHIER
 * @param status   PENDING, ACTIVE, or INACTIVE
 */
public record User(int id, String username, String password,
                   String name, String roles, String status) {
}
