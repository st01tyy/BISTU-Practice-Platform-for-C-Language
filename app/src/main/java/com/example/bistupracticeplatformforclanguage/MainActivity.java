package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bistupracticeplatformforclanguage.module.Student;
import com.example.bistupracticeplatformforclanguage.task.DownloadTask;

import java.io.File;

public class MainActivity extends AppCompatActivity
{
    private Button btn_refresh;
    private Button btn_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //获取Student对象
        Student student = (Student) getIntent().getSerializableExtra("student");

        //获取控件
        btn_refresh = (Button) findViewById(R.id.btn_refresh);
        btn_exit = (Button) findViewById(R.id.btn_exit);

        //初始化
        TextView text_name = (TextView) findViewById(R.id.text_name);
        text_name.setText(student.getStudentName());

        //设置按钮事件反应器
        btn_exit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //删除自动登录文件
                File autoLogin = new File(Environment.getExternalStorageDirectory().getPath() + "/c_practice/auto_login.txt");
                if(autoLogin.exists())
                    autoLogin.delete();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_refresh.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                DownloadTask downloadTask = new DownloadTask(MainActivity.this);
                downloadTask.execute();
            }
        });
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
