package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.bistupracticeplatformforclanguage.fragment.SelectionFragment;
import com.example.bistupracticeplatformforclanguage.fragment.TrueFalseFragment;
import com.example.bistupracticeplatformforclanguage.module.Mistake;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.util.List;

public class MistakeBookActivity extends AnswerQuestionActivity
{
    private List<Object> mistakeList;
    private int position;

    private Button btn_back;
    private TextView text_questionId;
    private TextView text_difficulty;
    private Button btn_delete;
    private TextView text_description;
    private FrameLayout frameLayout;
    private Button btn_judge;
    private TextView text_judge;
    private Button btn_previous;
    private TextView text_index;
    private Button btn_next;

    private String ans;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mistake_book);

        Intent intent = getIntent();
        mistakeList = (List<Object>) intent.getSerializableExtra("mistakeList");
        position = intent.getIntExtra("position", 0);

        btn_back = (Button) findViewById(R.id.btn_back);
        text_questionId = (TextView) findViewById(R.id.text_question_id);
        text_difficulty = (TextView) findViewById(R.id.text_difficulty);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        text_description = (TextView) findViewById(R.id.text_description);
        frameLayout = (FrameLayout) findViewById(R.id.frame_layout);
        btn_judge = (Button) findViewById(R.id.btn_judge);
        text_judge = (TextView) findViewById(R.id.text_judge);
        btn_previous = (Button) findViewById(R.id.btn_previous_question);
        text_index = (TextView) findViewById(R.id.text_index);
        btn_next = (Button) findViewById(R.id.btn_next_question);

        initialize();
    }

    private void initialize()
    {
        btn_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new AlertDialog.Builder(MistakeBookActivity.this).setTitle("确定要将这道题从错题本中移除吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                Object o = mistakeList.get(position);
                                if(o instanceof TrueFalseQuestion)
                                {
                                    TrueFalseQuestion question = (TrueFalseQuestion) o;
                                    LitePal.deleteAll(Mistake.class, "questionid=?", Integer.toString(question.getQuestionId()));
                                }
                                else
                                {
                                    MultipleChoiceQuestion question = (MultipleChoiceQuestion) o;
                                    LitePal.deleteAll(Mistake.class, "questionid=?", Integer.toString(question.getQuestionId()));
                                }
                                mistakeList.remove(position);
                                if(mistakeList.size() == 0)
                                    finish();
                                else if(mistakeList.size() <= position)
                                {
                                    while(mistakeList.size() <= position)
                                        position--;
                                    updateUI();
                                }
                                else
                                    updateUI();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                return;
                            }
                        }).show();
            }
        });

        btn_judge.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(ans == null)
                {
                    text_judge.setText("你还没有选择任何选项！");
                    text_judge.setTextColor(getResources().getColor(R.color.red));
                }
                else
                {
                    Object o = mistakeList.get(position);
                    if(o instanceof TrueFalseQuestion)
                    {
                        TrueFalseQuestion question = (TrueFalseQuestion) o;
                        if(ans.equals(question.getAnswer()))
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
                        MultipleChoiceQuestion question = (MultipleChoiceQuestion) o;
                        if(ans.equals(question.getAnswer()))
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
                }
            }
        });

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

        btn_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(position + 1 < mistakeList.size())
                {
                    position++;
                    updateUI();
                }
            }
        });

        updateUI();
    }

    private void updateUI()
    {
        Object o = mistakeList.get(position);
        if(o instanceof TrueFalseQuestion)
        {
            TrueFalseQuestion question = (TrueFalseQuestion) o;
            text_questionId.setText(Integer.toString(question.getQuestionId()));
            String difficulty = question.getDifficulty();
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
            text_description.setText(question.getDescription());
            replaceFragment(new TrueFalseFragment(MistakeBookActivity.this));
        }
        else
        {
            MultipleChoiceQuestion question = (MultipleChoiceQuestion) o;
            text_questionId.setText(Integer.toString(question.getQuestionId()));
            String difficulty = question.getDifficulty();
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
            text_description.setText(question.getDescription());
            replaceFragment(new SelectionFragment(question, MistakeBookActivity.this));
        }
        text_index.setText(Integer.toString(position + 1) + "/" + Integer.toString(mistakeList.size()));
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void updateAnswer(String ans)
    {
        this.ans = ans;
    }

    @Override
    public String getSelectedAnswers() {
        return null;
    }
}
