package com.example.bistupracticeplatformforclanguage.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bistupracticeplatformforclanguage.PreparePracticeActivity;
import com.example.bistupracticeplatformforclanguage.PracticeActivity;
import com.example.bistupracticeplatformforclanguage.R;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

import java.io.Serializable;
import java.util.List;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder>
{
    private List<Object> list;
    private PreparePracticeActivity activity;
    private String stage;

    public QuestionAdapter(List<Object> list, PreparePracticeActivity activity, String stage) {
        this.list = list;
        this.activity = activity;
        this.stage = stage;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView text_itemQuestion;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            text_itemQuestion = (TextView) itemView.findViewById(R.id.text_item_question);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        Object object = list.get(position);
        if(object instanceof MultipleChoiceQuestion)
        {
            MultipleChoiceQuestion question = (MultipleChoiceQuestion) object;
            holder.text_itemQuestion.setText(question.getQuestionId() + ". " + question.getDescription());
        }
        else
        {
            TrueFalseQuestion question = (TrueFalseQuestion) object;
            holder.text_itemQuestion.setText(question.getQuestionId() + ". " + question.getDescription());
        }

        holder.text_itemQuestion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(activity, PracticeActivity.class);
                intent.putExtra("stage", stage);
                intent.putExtra("position", position);
                intent.putExtra("list", (Serializable)list);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
