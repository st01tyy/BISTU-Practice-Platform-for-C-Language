package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bistupracticeplatformforclanguage.fragment.SelectionFragment;
import com.example.bistupracticeplatformforclanguage.fragment.TrueFalseFragment;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class PracticeActivity extends CustomActivity
{
    private String stage;
    private String answer = null;
    private int position = 0;
    private List<Object> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);

        //初始化
        initialize();
    }

    @Override
    public void updateAnswer(String ans)
    {
        if(ans == null)
            Log.d("updateAnswer", "null");
        else
            Log.d("updateAnswer", ans);
        answer = ans;
    }

    private void initialize()   //初始化数据和界面
    {
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

        //获取阶段名
        stage = getIntent().getStringExtra("stage");

        //获取可能的题目列表
        List<Object> t = (List<Object>) getIntent().getSerializableExtra("list");
        if(t != null)
            list = t;

        //获取可能的序号
        position = getIntent().getIntExtra("position", 0);

        if(list.size() == 0)
        {
            //获取选择题题目列表
            long id = 1;
            MultipleChoiceQuestion multipleChoiceQuestion = LitePal.find(MultipleChoiceQuestion.class, id);
            while(multipleChoiceQuestion != null)
            {
                if(multipleChoiceQuestion.getStage().equals(stage))
                    list.add(multipleChoiceQuestion);
                id++;
                multipleChoiceQuestion = LitePal.find(MultipleChoiceQuestion.class, id);
            }

            //获取判断题列表
            id = 1;
            TrueFalseQuestion trueFalseQuestion = LitePal.find(TrueFalseQuestion.class, id);
            while(trueFalseQuestion != null)
            {
                if(trueFalseQuestion.getStage().equals(stage))
                    list.add(trueFalseQuestion);
                id++;
                trueFalseQuestion = LitePal.find(TrueFalseQuestion.class, id);
            }

            Log.d("PracticeActivity", "题目列表长度：" + Integer.toString(list.size()));
        }

        Button btn_previous = (Button) findViewById(R.id.btn_previous_question);
        btn_previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(position - 1 >= 0)
                {
                    position--;
                    updateUI();
                }
            }
        });

        Button btn_next = (Button) findViewById(R.id.btn_next_question);
        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(position + 1 < list.size())
                {
                    position++;
                    updateUI();
                }
            }
        });

        Button btn_judge = (Button) findViewById(R.id.btn_judge);
        btn_judge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                TextView text_judge = (TextView) findViewById(R.id.text_judge);
                if(answer == null)
                {
                    text_judge.setText("你还没有选择任何选项！");
                    text_judge.setTextColor(getResources().getColor(R.color.red));
                }
                else
                {
                    Object object = list.get(position);
                    if(object instanceof  MultipleChoiceQuestion)
                    {
                        MultipleChoiceQuestion question = (MultipleChoiceQuestion) object;
                        if(question.getAnswer().equals(answer))
                        {
                            text_judge.setText("答案正确");
                            text_judge.setTextColor(getResources().getColor(R.color.green));
                        }
                        else
                        {
                            text_judge.setText("答案错误，正确答案为：" + question.getAnswer());
                            text_judge.setTextColor(getResources().getColor(R.color.red));
                        }
                    }
                    else
                    {
                        TrueFalseQuestion question = (TrueFalseQuestion) object;
                        if(question.getAnswer().equals(answer))
                        {
                            text_judge.setText("答案正确");
                            text_judge.setTextColor(getResources().getColor(R.color.green));
                        }
                        else
                        {
                            text_judge.setText("答案错误");
                            text_judge.setTextColor(getResources().getColor(R.color.red));
                        }
                    }
                }
            }
        });

        //初始化界面
        updateUI();
    }

    private void updateUI()
    {
        TextView text_questionId = (TextView) findViewById(R.id.text_question_id);
        TextView text_difficulty = (TextView) findViewById(R.id.text_difficulty);
        TextView text_description = (TextView) findViewById(R.id.text_description);
        TextView text_index = (TextView) findViewById(R.id.text_index);
        TextView text_judge = (TextView) findViewById(R.id.text_judge);
        Object question = list.get(position);
        if(question instanceof MultipleChoiceQuestion)
        {
            MultipleChoiceQuestion multipleChoiceQuestion = (MultipleChoiceQuestion) question;
            text_questionId.setText(Integer.toString(multipleChoiceQuestion.getQuestionId()));
            String difficulty = multipleChoiceQuestion.getDifficulty();
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
            text_description.setText(multipleChoiceQuestion.getDescription());
            replaceFragment(new SelectionFragment(multipleChoiceQuestion, this));
        }
        else
        {
            TrueFalseQuestion multipleChoiceQuestion = (TrueFalseQuestion) question;
            text_questionId.setText(Integer.toString(multipleChoiceQuestion.getQuestionId()));
            String difficulty = multipleChoiceQuestion.getDifficulty();
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
            text_description.setText(multipleChoiceQuestion.getDescription());
            replaceFragment(new TrueFalseFragment(this));
        }
        text_index.setText(Integer.toString(position + 1) + "/" + Integer.toString(list.size()));
        text_judge.setText("");
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

}
