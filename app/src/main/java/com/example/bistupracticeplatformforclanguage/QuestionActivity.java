package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;

import org.litepal.LitePal;

import java.util.Comparator;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {
    private TextView text_questionId;
    private TextView text_difficulty;
    private TextView text_description;
    private TextView text_selectionA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        //获取控件
        text_questionId = (TextView) findViewById(R.id.text_question_id);
        text_difficulty = (TextView) findViewById(R.id.text_difficulty);
        text_description = (TextView) findViewById(R.id.text_description);
        text_selectionA = (TextView) findViewById(R.id.text_selection_a);

        List<MultipleChoiceQuestion> list = LitePal.findAll(MultipleChoiceQuestion.class);

        for(MultipleChoiceQuestion multipleChoiceQuestion : list)
        {
            String difficulty = multipleChoiceQuestion.getDifficulty();
            Log.d("QuestionActivity", difficulty);
        }

        text_questionId.setText(Integer.toString(list.get(0).getQuestionId()));
        String difficulty = list.get(list.size() - 1).getDifficulty();
        Log.d("QuestionActivity", difficulty);
        if(difficulty.equals("简单"))
        {
            text_difficulty.setText("简单");
            text_difficulty.setTextColor(getResources().getColor(R.color.black));
            text_difficulty.setBackgroundResource(R.drawable.green_round_corner_label);
        }
        else if(difficulty.equals("普通"))
        {
            text_difficulty.setText("普通");
            text_difficulty.setTextColor(getResources().getColor(R.color.white));
            text_difficulty.setBackgroundResource(R.drawable.blue_round_corner_label);
        }
        else
        {
            text_difficulty.setText("困难");
            text_difficulty.setTextColor(getResources().getColor(R.color.white));
            text_difficulty.setBackgroundResource(R.drawable.red_round_corner_label);
        }
        text_description.setText(list.get(0).getDescription());
        text_selectionA.setText(list.get(0).getSelectionA());

        //设置返回键事件反应器
        Button btn_back = (Button) findViewById(R.id.btn_back);
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
