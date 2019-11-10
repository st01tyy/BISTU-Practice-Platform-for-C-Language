package com.example.bistupracticeplatformforclanguage.module;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class Mistake extends LitePalSupport implements Serializable
{
    private int type;
    private int questionId;

    public Mistake(int type, int questionId) {
        this.type = type;
        this.questionId = questionId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
