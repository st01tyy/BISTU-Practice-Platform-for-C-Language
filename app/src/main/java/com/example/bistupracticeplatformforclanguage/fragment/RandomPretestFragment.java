package com.example.bistupracticeplatformforclanguage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bistupracticeplatformforclanguage.Function;
import com.example.bistupracticeplatformforclanguage.PrepareTestActivity;
import com.example.bistupracticeplatformforclanguage.R;
import com.example.bistupracticeplatformforclanguage.adapter.PretestRandomAdapter;
import com.example.bistupracticeplatformforclanguage.task.PrepareTestTask;

import java.util.ArrayList;
import java.util.List;

public class RandomPretestFragment extends Fragment
{
    /*
    随机测试选项碎片，用于选择题目范围
     */

    private PrepareTestActivity activity;   //测试准备阶段的活动引用，用于设置列表布局和开始答题活动

    private RecyclerView pretestRandom; //随机测试的阶段列表，用于选择题目范围
    private NumberPicker numberPicker;
    private Button btn_randomStartTest; //随机测试开始按钮

    public RandomPretestFragment(PrepareTestActivity activity)  //构造函数
    {
        super();
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        /*
        界面载入函数
         */

        View view = inflater.inflate(R.layout.fragment_random, container, false);   //读取相应的布局文件

        //获取界面控件
        pretestRandom = (RecyclerView) view.findViewById(R.id.list_pretest_random);
        numberPicker = (NumberPicker) view.findViewById(R.id.numberPicker);
        btn_randomStartTest = (Button) view.findViewById(R.id.btn_random_start_test);

        initialize();   //初始化界面
        return view;
    }

    private void initialize()
    {
        /*
        初始化界面函数
         */

        //配置阶段列表
        final List<String> stageList = Function.findStage();  //获取阶段名称列表
        PretestRandomAdapter adapter = new PretestRandomAdapter(stageList); //创建阶段列表的适配器
        LinearLayoutManager manager = new LinearLayoutManager(activity);    //创建线性布局管理
        manager.setOrientation(RecyclerView.VERTICAL);  //设置垂直布局方向
        pretestRandom.setLayoutManager(manager);
        pretestRandom.setAdapter(adapter);

        numberPicker.setMinValue(10);
        numberPicker.setMaxValue(20);
        numberPicker.setValue(10);

        btn_randomStartTest.setOnClickListener(new View.OnClickListener()   //开始测试
        {
            @Override
            public void onClick(View view)
            {
                PretestRandomAdapter temp = (PretestRandomAdapter) pretestRandom.getAdapter();
                boolean[] selection = temp.getSelection();
                List<String> selectedStageList = new ArrayList<>();
                for(int i = 0; i < selection.length; i++)
                {
                    if(selection[i])
                        selectedStageList.add(stageList.get(i));
                }
                PrepareTestTask task = new PrepareTestTask(activity, selectedStageList, numberPicker.getValue());
                task.execute();
            }
        });
    }

    private List<Object> getRandomQuestionList(List<String> stageList)
    {
        return null;
    }

}
