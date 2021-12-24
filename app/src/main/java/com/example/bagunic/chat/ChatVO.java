package com.example.bagunic.chat;

public class ChatVO {
    private String nick;
    private String chat;
    private String day;

    public String getNick() {
        return nick;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public ChatVO(String nick, String chat, String day) {
        this.nick = nick;
        this.chat = chat;
        this.day = day;
    }

    @Override
    public String toString() {
        return "ChatVO{" +
                "nick='" + nick + '\'' +
                ", chat='" + chat + '\'' +
                ", day='" + day + '\'' +
                '}';
    }
}
