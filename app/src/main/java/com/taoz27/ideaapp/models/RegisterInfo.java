package com.taoz27.ideaapp.models;

import java.io.Serializable;

/**
 * Created by taoz27 on 2017/11/18.
 */

public class RegisterInfo implements Serializable{
    private static final long serialVersionUID = 6941224016988002091L;

    private int id;
    private String email;
    private String password;
    private int role;
    private String institution;
    private String question;
    private String answer;
    private int ifchecked;
    private long createtime;
    private long updatetime;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getRole() {
        return role;
    }

    public String getInstitution() {
        return institution;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int isIfchecked() {
        return ifchecked;
    }

    public long getCreatetime() {
        return createtime;
    }

    public long getUpdatetime() {
        return updatetime;
    }
}
