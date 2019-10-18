package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bistupracticeplatformforclanguage.module.Student;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取Student对象
        Student student = (Student) getIntent().getSerializableExtra("student");

        //初始化
        TextView text_name = (TextView) findViewById(R.id.text_name);
        text_name.setText(student.getStudentName());
    }

    @Override
    public void onBackPressed()
    {
        //确认退出对话框
        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                }).show();
    }
}
