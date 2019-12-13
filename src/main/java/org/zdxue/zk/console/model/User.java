package org.zdxue.zk.console.model;

import java.io.Serializable;

/**
 * @author xuezhongde
 */
public class User implements Serializable {
    private static final long serialVersionUID = 4884472574877188184L;

    private String username;
    private String password;
    private UserRole role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
