package com.example.arcrca;

public enum UserRole {
    ADMIN, USER, MODERATOR;

    @Override
    public String toString() {
        return "ROLE_" + name();
    }
}
