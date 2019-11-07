package com.example.bistupracticeplatformforclanguage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bistupracticeplatformforclanguage.R;

import java.util.List;

public class PretestRandomAdapter extends RecyclerView.Adapter<PretestRandomAdapter.ViewHolder>
{
    /*
    用于随机测试碎片的阶段列表适配器
     */

    private List<String> stageList; //阶段名称列表

    public PretestRandomAdapter(List<String> stageList)    //构造函数，传入阶段名称列表
    {
        this.stageList = stageList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        /*
        阶段列表的元素控件保存器
         */

        View root;    //列表元素的底层界面
        CheckBox check_itemRandom;  //每一个阶段的复选框

        public ViewHolder(@NonNull View itemView)  //构造函数
        {
            super(itemView);
            this.root = itemView;
            check_itemRandom = (CheckBox) itemView.findViewById(R.id.check_item_random); //获取复选框
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)   //为每个元素设置界面并创建控件保存器
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pretest_random, parent, false);  //读取布局文件
        ViewHolder viewHolder = new ViewHolder(view);   //创建控件保存器
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)  //设置元素控件
    {
        CheckBox check_itemRandom = holder.check_itemRandom;
        check_itemRandom.setText(stageList.get(position));  //设置复选框的阶段名
        check_itemRandom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()    //提交复选框选中状态
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(b)
                {

                }
                else
                {

                }
            }
        });
    }

    @Override
    public int getItemCount()   //获取列表长度
    {
        return stageList.size();
    }

}
