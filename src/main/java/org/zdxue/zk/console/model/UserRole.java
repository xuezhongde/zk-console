package org.zdxue.zk.console.model;

/**
 * @author xuezhongde
 */
public enum UserRole {

    ORDINARY(0, "user"),
    ADMIN(1, "admin");

    private int roleId;
    private String roleName;

    UserRole(int roleId, String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public static UserRole of(int roleId) {
        for (UserRole role : values()) {
            if (roleId == role.roleId) {
                return role;
            }
        }

        throw new IllegalArgumentException("invalid roleId - " + roleId);
    }
}
