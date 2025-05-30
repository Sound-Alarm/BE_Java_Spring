package com.example.demo2.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private String password;
    private String email;
    private boolean isOnline;
    private LocalDateTime lastLogin;
    private LocalDateTime lastLogout;
    private String timeSinceLogout;
    private Role role;
    private ConveyorBelt conveyorBelt;

    public Role getRole() {
        return role;
    }

    public User() {
    }

    public ConveyorBelt getConveyorBelt() {
        return conveyorBelt;
    }

    public void setConveyorBelt(ConveyorBelt conveyorBelt) {
        this.conveyorBelt = conveyorBelt;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User(Object o, String username, String encode, Set<String> user) {

    }

    public String calculateTimeSinceLogout(LocalDateTime lastLogout) {
        LocalDateTime now = LocalDateTime.now();
        long minutes = Duration.between(lastLogout, now).toMinutes();
        if (minutes < 60) {
            return minutes + " phút trước";
        } else if (minutes < 1440) { // 24 hours
            return (minutes / 60) + " giờ trước";
        } else {
            return (minutes / 1440) + " ngày trước";
        }
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public LocalDateTime getLastLogout() {
        return lastLogout;
    }

    public String getTimeSinceLogout() {
        return timeSinceLogout;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setLastLogout(LocalDateTime lastLogout) {
        this.lastLogout = lastLogout;
    }

    public void setTimeSinceLogout(String timeSinceLogout) {
        this.timeSinceLogout = timeSinceLogout;
    }

}