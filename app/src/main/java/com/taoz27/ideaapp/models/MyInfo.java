package com.taoz27.ideaapp.models;

import java.io.Serializable;

/**
 * Created by taoz27 on 2017/11/17.
 */

public class MyInfo implements Serializable{
    private static final long serialVersionUID = -3912955175633759029L;

    private int id;
    private String email;
    private String institution;
    private String question;
    private String answer;

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
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
}
