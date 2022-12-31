package model;

public class User {
    private final String nickname;
    private final Integer pin; // belong to the interval 8000:8010
    private final String email; // allows the user to recover its passoword

    public User(String nickname, Integer pin, String email) {
        this.nickname = nickname;
        this.pin = pin;
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public Integer getPin() {
        return pin;
    }

    public String getEmail() {
        return email;
    }
}
