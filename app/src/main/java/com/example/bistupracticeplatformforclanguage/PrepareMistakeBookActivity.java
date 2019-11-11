package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.bistupracticeplatformforclanguage.adapter.PrepareMistakeAdapter;
import com.example.bistupracticeplatformforclanguage.module.Mistake;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class PrepareMistakeBookActivity extends AppCompatActivity
{
    private Button btn_back;
    private RecyclerView list_mistake;

    private List<Object> mistakeList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_mistake_book);

        btn_back = (Button) findViewById(R.id.btn_back);
        list_mistake = (RecyclerView) findViewById(R.id.list_mistake);

        initialize();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        refreshAdapter();
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

        //refreshAdapter();
    }

    public void refreshAdapter()
    {
        List<Object> mistakeList = new ArrayList<>();
        List<Mistake> temp = LitePal.findAll(Mistake.class);
        for(Mistake m : temp)
        {
            if(m.getType() == 0)
            {
                List<TrueFalseQuestion> t = LitePal.where("questionid=?", Integer.toString(m.getQuestionId())).find(TrueFalseQuestion.class);
                if(t != null && t.size() > 0)
                    mistakeList.add(t.get(0));
            }
            else
            {
                List<MultipleChoiceQuestion> t = LitePal.where("questionid=?", Integer.toString(m.getQuestionId())).find(MultipleChoiceQuestion.class);
                if(t != null && t.size() > 0)
                    mistakeList.add(t.get(0));
            }
        }

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        PrepareMistakeAdapter adapter = new PrepareMistakeAdapter(this, mistakeList);
        list_mistake.setLayoutManager(manager);
        list_mistake.setAdapter(adapter);
    }
}
