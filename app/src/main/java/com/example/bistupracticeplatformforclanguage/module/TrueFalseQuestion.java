package com.example.bistupracticeplatformforclanguage.module;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class TrueFalseQuestion extends LitePalSupport implements Serializable
{
    private int questionId;
    private String description;
    private String answer;
    private String stage;
    private String difficulty;

    public TrueFalseQuestion() {
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }
}
