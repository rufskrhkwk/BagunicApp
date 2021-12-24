package com.example.bagunic.sellbagunic;

import java.io.Serializable;

public class selectVO implements Serializable {

    private String possible;
    private String use;

    public selectVO() {

    }
    public selectVO(String possible, String use) {
        this.possible = possible;
        this.use = use;

    }

    public String getId() {
        return possible;
    }
    public String getPw() {
        return use;
    }

    @Override
    public String toString() {
        return "AndMemberVO{" +
                "possible='" + possible + '\'' +
                ", use='" + use + '\'' +
                '}';
    }
}




