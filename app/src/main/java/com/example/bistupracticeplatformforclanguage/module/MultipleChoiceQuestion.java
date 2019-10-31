package com.example.bistupracticeplatformforclanguage.module;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class MultipleChoiceQuestion extends LitePalSupport implements Serializable
{
    private int questionId;
    private String description;
    private String selectionA;
    private String selectionB;
    private String selectionC;
    private String selectionD;
    private String answer;
    private String stage;
    private String difficulty;

    public MultipleChoiceQuestion() {
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

    public String getSelectionA() {
        return selectionA;
    }

    public void setSelectionA(String selectionA) {
        this.selectionA = selectionA;
    }

    public String getSelectionB() {
        return selectionB;
    }

    public void setSelectionB(String selectionB) {
        this.selectionB = selectionB;
    }

    public String getSelectionC() {
        return selectionC;
    }

    public void setSelectionC(String selectionC) {
        this.selectionC = selectionC;
    }

    public String getSelectionD() {
        return selectionD;
    }

    public void setSelectionD(String selectionD) {
        this.selectionD = selectionD;
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
