package com.example.homeschoolplanner;

import java.io.Serializable;

public class User implements Serializable {
    public String user_id;
    public String email;
    public boolean is_parent;
    public String password;
    public String user_name;


    User() {
        this.user_id = null;
        this.is_parent = true;
        this.password = null;
        this.user_name = null;
    }

    User(String user_id, boolean is_parent, String password, String user_name, String email) {
        this.user_id = user_id;
        this.is_parent = is_parent;
        this.password = password;
        this.user_name = user_name;
        this.email = email;
    }
/**
    String getUserId() {
        return user_id;
    }

    Boolean getIsParent() {
        return is_parent;
    }

    String getPassword() {
        return password;
    }

    String getEmail() {  return email;  }

    void setPassword(String new_password) {
        password = new_password;
    }

    String getUserName() {
        return user_name;
    }

*/
}
