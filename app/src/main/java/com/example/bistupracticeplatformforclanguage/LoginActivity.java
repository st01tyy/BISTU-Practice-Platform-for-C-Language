package com.example.bistupracticeplatformforclanguage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bistupracticeplatformforclanguage.module.LoginForm;
import com.example.bistupracticeplatformforclanguage.module.Student;
import com.example.bistupracticeplatformforclanguage.task.LoginTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

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

        //验证存储与网络权限
        if(ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        if(ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.INTERNET}, 2);

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

        //创建应用目录
        File projectDir = new File(Environment.getExternalStorageDirectory().getPath() + "/c_practice");
        if(!projectDir.exists())
            projectDir.mkdir();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        //读取自动登录记录
        File autoLogin = new File(Environment.getExternalStorageDirectory().getPath() + "/c_practice/auto_login.txt");
        if(autoLogin.exists())
        {
            Log.d("LoginActivity", "auto login file exists");
            try
            {
                //读取文件
                BufferedReader reader = new BufferedReader(new FileReader(autoLogin));
                String id = reader.readLine();
                String pw = reader.readLine();
                reader.close();
                Log.d("LoginActivity", "auto login file: " + id + " " + pw);

                //向服务器验证登录
                LoginTask loginTask = new LoginTask(this);
                loginTask.execute(new LoginForm(id, pw));
            }
            catch (Exception e)
            {
                Log.e("LoginActivity", e.getMessage());
                e.printStackTrace();
            }
        }
        else
            Log.d("LoginActivity", "auto login file doesn't exist");
    }

    //处理权限请求结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if(grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    //登录的一系列操作
    private void login()
    {
        String id, pw;
        boolean autoLogin;

        //判断输入框是否有输入内容
        if(input_id.getText() == null)
            return;
        else
            id = input_id.getText().toString();
        if(input_pw.getText() == null)
            return;
        else
            pw = input_pw.getText().toString();
        autoLogin = check_autoLogin.isChecked();

        Log.d("LoginActivity", "user submit: " + id + " " + pw + " " + Boolean.toString(autoLogin));

        //向服务器验证登录
        LoginTask loginTask = new LoginTask(this);
        loginTask.execute(new LoginForm(id, pw, autoLogin));
    }

    //若登录失败
    public void onLoginFail()
    {
        TextView text_warning = (TextView) findViewById(R.id.text_warning);
        text_warning.setVisibility(View.VISIBLE);
    }

    //保存登录信息
    public void saveAccount(String id, String pw)
    {
        File autoLogin = new File(Environment.getExternalStorageDirectory().getPath() + "/c_practice/auto_login.txt");
        if(autoLogin.exists())
            autoLogin.delete();
        try
        {
            autoLogin.createNewFile();
            PrintWriter writer = new PrintWriter(autoLogin);
            writer.write(id + "\r\n");
            writer.write(pw + "\r\n");
            writer.close();
            Log.d("LoginActivity", "file is successfully saved");
        }
        catch (IOException e)
        {
            Log.e("LoginActivity", e.getMessage());
            e.printStackTrace();
        }
    }


}
