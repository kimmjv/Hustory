package com.kang.project.login;

import java.util.HashMap;
import java.util.Map;

public class Member {
    private String email;
    private String pw;
    private String name;
    private String phone;
    private String role;

    public Member(String email, String pw, String name, String phone, String role) {
        this.email = email;
        this.pw = pw;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // Member를 HashMap 형태로 값을 변환하여 데이터베이스에 저장하기 위한 메서드
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("email", this.email);
        result.put("pw", this.pw);
        result.put("name", this.name);
        result.put("phone", this.phone);
        result.put("role", this.role);

        return result;
    }

}
