package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bistupracticeplatformforclanguage.module.LoginForm;
import com.example.bistupracticeplatformforclanguage.module.Student;
import com.example.bistupracticeplatformforclanguage.task.LoginTask;

public class LoginActivity extends AppCompatActivity
{
    private EditText input_id;
    private EditText input_pw;
    private CheckBox check_autoLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取输入控件
        input_id = (EditText) findViewById(R.id.input_id);
        input_pw = (EditText) findViewById(R.id.input_pw);
        check_autoLogin = (CheckBox) findViewById(R.id.check_auto_login);

        //设置登录按钮事件反应器
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                login();
            }
        });
    }

    //登录的一系列操作
    private void login()
    {
        String id, pw;

        //判断输入框是否有输入内容
        if(input_id.getText() == null)
            return;
        else
            id = input_id.getText().toString();
        if(input_pw.getText() == null)
            return;
        else
            pw = input_pw.getText().toString();

        Log.d("LoginActivity", "user submit: " + id + " " + pw);

        //向服务器验证登录
        LoginTask loginTask = new LoginTask(this);
        loginTask.execute(new LoginForm(id, pw));
    }

    //若登录失败
    public void onLoginFail()
    {
        TextView text_warning = (TextView) findViewById(R.id.text_warning);
        text_warning.setVisibility(View.VISIBLE);
    }

}
