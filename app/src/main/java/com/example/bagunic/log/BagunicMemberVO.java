package com.example.bagunic.log;

import java.io.Serializable;

public class BagunicMemberVO implements Serializable {
    private String id;
    private String nick;
    private String phone;
    private String pw;
    public BagunicMemberVO(String id, String nick, String phone) {
        this.id = id;
        this.nick = nick;
        this.phone = phone;
    }
    public BagunicMemberVO(String id, String nick, String phone, String pw) {
        this.id = id;
        this.pw =pw;
        this.nick = nick;
        this.phone = phone;

    }
    public BagunicMemberVO(String id,String phone) {
        this.id = id;
        this.phone = phone;
    }
    public BagunicMemberVO(String id) {
        this.id = id;

    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getNick() {
        return nick;
    }

    public String getPhone() {
        return phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
