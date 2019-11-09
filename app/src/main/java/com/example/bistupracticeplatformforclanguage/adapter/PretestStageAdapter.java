package com.example.bistupracticeplatformforclanguage.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bistupracticeplatformforclanguage.Function;
import com.example.bistupracticeplatformforclanguage.PrepareTestActivity;
import com.example.bistupracticeplatformforclanguage.R;
import com.example.bistupracticeplatformforclanguage.SelfTestActivity;
import com.example.bistupracticeplatformforclanguage.task.PrepareTestTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PretestStageAdapter extends RecyclerView.Adapter<PretestStageAdapter.ViewHolder>
{
    private PrepareTestActivity activity;
    private List<String> stageList;

    public PretestStageAdapter(PrepareTestActivity activity, List<String> stageList)
    {
        this.activity = activity;
        this.stageList = stageList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder
    {
        View root;
        TextView text_stage;
        TextView text_startTest;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            this.root = itemView;
            text_stage = (TextView) root.findViewById(R.id.text_item_pretest_stage);
            text_startTest = (TextView) root.findViewById(R.id.text_stage_start_test);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pretest_stage, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        holder.text_stage.setText(stageList.get(position));
        holder.text_startTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                new AlertDialog.Builder(activity).setTitle("确定要进行" + stageList.get(position) + "自测吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                List<String> selectedStageList = new ArrayList<>();
                                selectedStageList.add(stageList.get(position));
                                PrepareTestTask task = new PrepareTestTask(activity, selectedStageList);
                                task.execute();
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
    }

    @Override
    public int getItemCount()
    {
        return stageList.size();
    }
}
