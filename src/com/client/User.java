package com.client;

import java.io.Serializable;

/**
 * @author 万锦辉
 * @version 0.1
 * user class
 */
public class User implements Serializable {
    private String id;
    private String password;

    public User(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
