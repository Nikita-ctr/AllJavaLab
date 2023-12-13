package org.example.fifth.domain;

public record Passanger(int id, String firstName, String lastName, String contactNumber, String email,
                     String password, boolean isLogin, boolean isAdmin) {
}