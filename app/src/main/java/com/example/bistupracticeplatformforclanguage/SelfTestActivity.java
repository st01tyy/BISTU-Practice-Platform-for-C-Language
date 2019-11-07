package com.example.bistupracticeplatformforclanguage;

import android.os.Bundle;

public class SelfTestActivity extends AnswerQuestionActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_test);
    }

    @Override
    public void updateAnswer(String ans) {

    }
}
