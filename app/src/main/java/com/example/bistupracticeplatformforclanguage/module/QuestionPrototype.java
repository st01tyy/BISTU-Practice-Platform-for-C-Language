package com.example.bistupracticeplatformforclanguage.module;

public class QuestionPrototype
{
    private String id;
    private String questionBh;
    private String answer;
    private String questionName;
    private String questionDetails;
    private String stage;
    private String difficulty;

    public QuestionPrototype() {
    }

    public QuestionPrototype(String id, String questionBh, String answer, String questionName, String questionDetails, String stage, String difficulty) {
        this.id = id;
        this.questionBh = questionBh;
        this.answer = answer;
        this.questionName = questionName;
        this.questionDetails = questionDetails;
        this.stage = stage;
        this.difficulty = difficulty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionBh() {
        return questionBh;
    }

    public void setQuestionBh(String questionBh) {
        this.questionBh = questionBh;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }

    public String getQuestionDetails() {
        return questionDetails;
    }

    public void setQuestionDetails(String questionDetails) {
        this.questionDetails = questionDetails;
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
