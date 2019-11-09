package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class TestFinishActivity extends AppCompatActivity
{
    private String title;
    private List<String> knowledgeList;
    private int correctRate;
    private List<Object> mistakeList;


    private Button btn_back;
    private TextView text_testTitle;
    private RecyclerView list_knowledge;
    private TextView text_correctRate;
    private TextView text_nMistake;
    private TextView text_add;
    private RecyclerView list_mistake;
    private Button btn_goto;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_finish);

        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        knowledgeList = (List<String>) intent.getSerializableExtra("knowledgeList");
        correctRate = intent.getIntExtra("correctRate", 100);
        mistakeList= (List<Object>) intent.getSerializableExtra("mistakeList");


        btn_back = (Button) findViewById(R.id.btn_back);
        text_testTitle = (TextView) findViewById(R.id.text_testTitle);
        list_knowledge = (RecyclerView) findViewById(R.id.list_knowledge);
        text_correctRate = (TextView) findViewById(R.id.text_correctRate);
        text_nMistake = (TextView) findViewById(R.id.text_nMistake);
        text_add = (TextView) findViewById(R.id.btn_add);
        list_mistake = (RecyclerView) findViewById(R.id.list_mistake);
        btn_goto = (Button) findViewById(R.id.btn_goto);

        initialize();
    }

    private void initialize()
    {
        btn_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
}
