package com.medhat.springboot.courseswebsite.dto;

public class UserDTO {

    private Integer personalId;
    private String userName;
    private String email;
    private String role;

    public UserDTO(Integer personalId, String userName, String email, String roleName) {
        this.personalId = personalId;
        this.userName = userName;
        this.email = email;
        this.role = roleName;
    }

    public Integer getPersonalId() {
        return personalId;
    }

    public void setPersonalId(Integer personalId) {
        this.personalId = personalId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + personalId +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", roleName='" + role + '\'' +
                '}';
    }
}
