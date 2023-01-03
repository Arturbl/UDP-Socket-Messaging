package com.trocamensagagens.model;

import java.io.Serializable;

public class Payload implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String nickname;
    private final String port;
    private final String message;

    public Payload(String nickname, String port, String message) {
        this.nickname = nickname;
        this.port = port;
        this.message = message;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPort() {
        return port;
    }

    public String getMessage() {
        return message;
    }



}
