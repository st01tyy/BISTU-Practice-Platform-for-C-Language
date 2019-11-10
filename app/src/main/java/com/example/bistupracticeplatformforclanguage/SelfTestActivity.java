package com.example.bistupracticeplatformforclanguage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bistupracticeplatformforclanguage.fragment.SelectionFragment;
import com.example.bistupracticeplatformforclanguage.fragment.TrueFalseFragment;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;
import com.example.bistupracticeplatformforclanguage.task.JudgeTestTask;

import java.util.List;

public class SelfTestActivity extends AnswerQuestionActivity
{
    //DATA
    private String title;   //测试标题
    private List<String> stageList; //阶段列表
    private List<Object> questionList;  //题目列表
    private String[] ans;   //答案列表
    private int position;   //当前位置

    //UI
    private TextView text_title;
    private TextView text_position;
    private TextView text_description;
    private Button btn_submit;
    private Button btn_previous;
    private TextView text_index;
    private Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_self_test);

        //获取数据
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        stageList = (List<String>) intent.getSerializableExtra("stageList");
        questionList = (List<Object>) intent.getSerializableExtra("questionList");
        ans = new String[questionList.size()];
        position = 0;

        //获取控件
        text_title = (TextView) findViewById(R.id.text_testTitle);
        text_position = (TextView) findViewById(R.id.text_question_position);
        text_description = (TextView) findViewById(R.id.text_description);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_previous = (Button) findViewById(R.id.btn_previous_question);
        text_index = (TextView) findViewById(R.id.text_index);
        btn_next = (Button) findViewById(R.id.btn_next_question);

        initialize();   //初始化
    }

    private void initialize()   //初始化函数
    {
        text_title.setText(title);  //测试标题

        btn_submit.setOnClickListener(new View.OnClickListener() //交卷
        {
            @Override
            public void onClick(View v)
            {
                for(String str : ans)
                {
                    if(str == null)
                    {
                        new AlertDialog.Builder(SelfTestActivity.this).setTitle("你还要未完成的题目，确定提交吗？")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        JudgeTestTask task = new JudgeTestTask(SelfTestActivity.this, title, stageList, questionList, ans);
                                        task.execute();
                                    }
                                })
                                .setNegativeButton("继续作答", new DialogInterface.OnClickListener()
                                {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        return;
                                    }
                                }).show();
                        return;
                    }
                }
                JudgeTestTask task = new JudgeTestTask(SelfTestActivity.this, title, stageList, questionList, ans);
                task.execute();
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener()  //上一题
        {
            @Override
            public void onClick(View v)
            {
                if(position - 1 >= 0)
                {
                    position--;
                    updateUI();
                }
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener()  //下一题
        {
            @Override
            public void onClick(View v)
            {
                if(position + 1 <questionList.size())
                {
                    position++;
                    updateUI();
                }
            }
        });

        updateUI();
    }

    private void updateUI() //根据题目序号更新界面
    {
        Object o = questionList.get(position);
        text_position.setText(Integer.toString(position + 1));  //设置序号
        if(o instanceof TrueFalseQuestion)
        {
            TrueFalseQuestion question = (TrueFalseQuestion) o;
            text_description.setText(question.getDescription());
            replaceFragment(new TrueFalseFragment(this));
        }
        else
        {
            MultipleChoiceQuestion question = (MultipleChoiceQuestion) o;
            text_description.setText(question.getDescription());
            replaceFragment(new SelectionFragment(question, this));
        }
        text_index.setText(Integer.toString(position + 1) + "/" + Integer.toString(questionList.size()));
    }


    @Override
    public void updateAnswer(String ans)
    {
        if(ans != null)
        {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < ans.length(); i++) {
                sb.append(ans.charAt(i));
            }
            this.ans[position] = sb.toString();
        }
        else
            this.ans[position] = null;
    }

    @Override
    public String getSelectedAnswers() {
        return ans[position];
    }

    @Override
    public void onBackPressed()
    {
        //确认退出对话框
        new AlertDialog.Builder(this).setTitle("确定要终止自测吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                })
                .setNegativeButton("继续测试", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        return;
                    }
                }).show();
    }

    private void replaceFragment(Fragment fragment)
    {
        /*
        替换碎片函数
         */

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
