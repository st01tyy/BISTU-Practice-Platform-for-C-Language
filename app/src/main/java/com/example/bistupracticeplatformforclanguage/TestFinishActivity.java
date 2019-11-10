package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bistupracticeplatformforclanguage.adapter.FinishTestKnowledgeAdapter;
import com.example.bistupracticeplatformforclanguage.adapter.FinishTestMistakeAdapter;
import com.example.bistupracticeplatformforclanguage.task.MistakeImportTask;

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

    private boolean isAdded = false;

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

        text_testTitle.setText(title);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        FinishTestKnowledgeAdapter adapter = new FinishTestKnowledgeAdapter(knowledgeList);
        list_knowledge.setLayoutManager(layoutManager);
        list_knowledge.setAdapter(adapter);

        if(correctRate >= 85)
        {
            text_correctRate.setBackgroundResource(R.drawable.green_round_corner_label);
            text_correctRate.setTextColor(getResources().getColor(R.color.black));
        }
        else if(correctRate >= 60)
        {
            text_correctRate.setBackgroundResource(R.drawable.blue_round_corner_label);
            text_correctRate.setTextColor(getResources().getColor(R.color.white));
        }
        else
        {
            text_correctRate.setBackgroundResource(R.drawable.red_round_corner_label);
            text_correctRate.setTextColor(getResources().getColor(R.color.white));
        }
        text_correctRate.setText(Integer.toString(correctRate) + "%");

        text_nMistake.setText("(" + Integer.toString(mistakeList.size()) + ")");

        text_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(isAdded)
                {
                    Toast.makeText(TestFinishActivity.this, "已添加", Toast.LENGTH_SHORT).show();
                    return;
                }
                MistakeImportTask task = new MistakeImportTask(TestFinishActivity.this, mistakeList);
                task.execute();
            }
        });

        LinearLayoutManager anotherLayoutManager = new LinearLayoutManager(this);
        anotherLayoutManager.setOrientation(RecyclerView.VERTICAL);
        FinishTestMistakeAdapter anotherAdapter = new FinishTestMistakeAdapter(mistakeList);
        list_mistake.setLayoutManager(anotherLayoutManager);
        list_mistake.setAdapter(anotherAdapter);
    }

    public void setAdded(boolean val)
    {
        this.isAdded = val;
    }

}
