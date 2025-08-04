package com.Billing_system_pahana_edu.model;

public final class User {
    private final String id;
    private final String name;
    private final String email;
    private final String username;
    private final String password;
    private final String role;

    // Private constructor to enforce use of Builder
    private User(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.email = builder.email;
        this.username = builder.username;
        this.password = builder.password;
        this.role = builder.role;
    }

    // Getters only (immutability enforced)
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    /**
     * Builder for User class.
     */
    public static class Builder {
        private String id;
        private String name;
        private String email;
        private String username;
        private String password;
        private String role;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setRole(String role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}

