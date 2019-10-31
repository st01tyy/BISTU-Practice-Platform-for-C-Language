package com.example.bistupracticeplatformforclanguage.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bistupracticeplatformforclanguage.PracticeActivity;
import com.example.bistupracticeplatformforclanguage.PreparePracticeActivity;
import com.example.bistupracticeplatformforclanguage.R;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.Stage;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class StageAdapter extends RecyclerView.Adapter<StageAdapter.ViewHolder>
{
    private List<Stage> list;
    private PreparePracticeActivity activity;

    public StageAdapter(List<String> nameList, PreparePracticeActivity activity)
    {
        list = new ArrayList<>();
        for(String name : nameList)
        {
            list.add(new Stage(name));
        }
        this.activity = activity;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        Button btn_bend;
        TextView text_stage;
        RecyclerView questionList;
        View view;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            btn_bend = (Button) itemView.findViewById(R.id.btn_bend);
            text_stage = (TextView) itemView.findViewById(R.id.text_stage);
            questionList = (RecyclerView) itemView.findViewById(R.id.question_list);
            view = itemView;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stage, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position)
    {
        final Stage stage = list.get(position);
        holder.text_stage.setText(stage.getName());
        holder.text_stage.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(activity, PracticeActivity.class);
                intent.putExtra("stage", stage.getName());
                activity.startActivity(intent);
            }
        });
        holder.btn_bend.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(stage.isBended())
                {
                    //获取题目名称列表
                    List<Object> questionList = new ArrayList<>();

                    //获取选择题
                    long id = 1;
                    MultipleChoiceQuestion multipleChoiceQuestion = LitePal.find(MultipleChoiceQuestion.class, id);
                    while(multipleChoiceQuestion != null)
                    {
                        if(multipleChoiceQuestion.getStage().equals(stage.getName()))
                            questionList.add(multipleChoiceQuestion);
                        id++;
                        multipleChoiceQuestion = LitePal.find(MultipleChoiceQuestion.class, id);
                    }

                    //获取判断题
                    id = 1;
                    TrueFalseQuestion trueFalseQuestion = LitePal.find(TrueFalseQuestion.class, id);
                    while(trueFalseQuestion != null)
                    {
                        if(trueFalseQuestion.getStage().equals(stage.getName()))
                            questionList.add(trueFalseQuestion);
                        id++;
                        trueFalseQuestion = LitePal.find(TrueFalseQuestion.class, id);
                    }

                    Log.d("StageAdapter", Integer.toString(questionList.size()));

                    QuestionAdapter questionAdapter = new QuestionAdapter(questionList, activity, stage.getName());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
                    layoutManager.setOrientation(RecyclerView.VERTICAL);

                    holder.questionList.setLayoutManager(layoutManager);
                    holder.questionList.setAdapter(questionAdapter);

                    holder.btn_bend.setBackgroundResource(R.mipmap.pic_not_bend);

                    stage.setBended(false);
                }
                else
                {
                    holder.questionList.setAdapter(null);
                    holder.btn_bend.setBackgroundResource(R.mipmap.pic_bend);

                    stage.setBended(true);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
