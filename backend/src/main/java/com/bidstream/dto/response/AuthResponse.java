package com.bidstream.dto.response;

public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private String userId;
    private String username;

    public AuthResponse(String token, String userId, String username) {
        this.token = token;
        this.userId = userId;
        this.username = username;
    }

    public AuthResponse(String token, String tokenType, String userId, String username) {
        this.token = token;
        this.tokenType = tokenType;
        this.userId = userId;
        this.username = username;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getTokenType() { return tokenType; }
    public void setTokenType(String tokenType) { this.tokenType = tokenType; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}