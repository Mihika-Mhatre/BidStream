package com.bidstream.model;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String passwordHash;
    private BigDecimal balance;
    private boolean active;

    public User() {
        this.balance = BigDecimal.ZERO;
        this.active = true;
    }

    public User(String username, String email, String passwordHash, BigDecimal balance) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.balance = balance;
        this.active = true;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean hasSufficientFunds(BigDecimal amount) {
        return balance != null && balance.compareTo(amount) >= 0;
    }

    public void debitBalance(BigDecimal amount) {
        if (!hasSufficientFunds(amount)) {
            throw new IllegalArgumentException("Insufficient balance");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void creditBalance(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }
}
