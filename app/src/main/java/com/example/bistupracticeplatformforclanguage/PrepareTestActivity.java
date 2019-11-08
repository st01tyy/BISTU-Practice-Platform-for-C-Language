package com.example.bistupracticeplatformforclanguage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

import com.example.bistupracticeplatformforclanguage.fragment.RandomPretestFragment;
import com.example.bistupracticeplatformforclanguage.fragment.StagePretestFragment;

public class PrepareTestActivity extends AppCompatActivity
{
    /*
    测试的准备阶段，分为阶段测试和随机测试
     */

    private Button btn_back;    //返回按钮
    private CheckBox check_random;  //随机测试复选框
    private CheckBox check_stage;   //阶段测试复选框
    private FrameLayout frame_pretest;  //用于显示两种碎片的帧布局

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prepare_test);

        //获取界面控件
        btn_back = (Button) findViewById(R.id.btn_back);
        check_random = (CheckBox) findViewById(R.id.check_random);
        check_stage = (CheckBox) findViewById(R.id.check_stage);
        frame_pretest = (FrameLayout) findViewById(R.id.frame_pretest);

        //初始化界面
        initialize();
    }

    private void initialize()
    {
        /*
        初始化界面函数
         */

        btn_back.setOnClickListener(new View.OnClickListener()  //返回至主界面
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });

        check_random.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()    //勾选或取消勾选随机测试复选框
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)   //勾选复选框
                {
                    //显示随机测试碎片
                    RandomPretestFragment fragment = new RandomPretestFragment(PrepareTestActivity.this);   //创建碎片
                    replaceFragment(fragment);  //替换碎片
                    check_stage.setChecked(false);  //取消勾选阶段测试复选框
                }
                else    //取消勾选
                {
                    if(!check_stage.isChecked())    //两个复选框均没有被勾选
                    {
                        replaceFragment(new Fragment());  //删除碎片
                    }
                }
            }
        });

        check_stage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() //勾选或取消勾选阶段测试复选框
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)   //勾选复选框
                {
                    StagePretestFragment fragment = new StagePretestFragment(PrepareTestActivity.this);
                    replaceFragment(fragment);
                    check_random.setChecked(false); //取消勾选随机测试复选框
                }
                else    //取消勾选
                {
                    if(!check_random.isChecked())    //两个复选框均没有被勾选
                    {
                        replaceFragment(new Fragment());  //删除碎片
                    }
                }
            }
        });
    }

    private void replaceFragment(Fragment fragment)
    {
        /*
        替换碎片函数
         */

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_pretest, fragment);
        fragmentTransaction.commit();
    }

}
