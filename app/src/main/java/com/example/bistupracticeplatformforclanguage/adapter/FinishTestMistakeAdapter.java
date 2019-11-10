package com.example.bistupracticeplatformforclanguage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bistupracticeplatformforclanguage.Function;
import com.example.bistupracticeplatformforclanguage.R;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

import java.util.List;

public class FinishTestMistakeAdapter extends RecyclerView.Adapter<FinishTestMistakeAdapter.ViewHolder>
{
    private List<Object> mistakeList;

    public FinishTestMistakeAdapter(List<Object> mistakeList)
    {
        this.mistakeList = mistakeList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView text_questionId;
        TextView text_description;
        TextView text_knowledge;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            text_questionId = (TextView) itemView.findViewById(R.id.text_item_questionId);
            text_description = (TextView) itemView.findViewById(R.id.text_item_description);
            text_knowledge = (TextView) itemView.findViewById(R.id.text_item_knowledge);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_test_finish_mistake, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Object o = mistakeList.get(position);
        if(o instanceof TrueFalseQuestion)
        {
            TrueFalseQuestion question = (TrueFalseQuestion) o;
            holder.text_questionId.setText(Integer.toString(question.getQuestionId()));
            holder.text_description.setText(question.getDescription());
            holder.text_knowledge.setText(Function.getKnowledge(question.getStage()));
        }
        else
        {
            MultipleChoiceQuestion question = (MultipleChoiceQuestion) o;
            holder.text_questionId.setText(Integer.toString(question.getQuestionId()));
            holder.text_description.setText(question.getDescription());
            holder.text_knowledge.setText(Function.getKnowledge(question.getStage()));
        }
    }

    @Override
    public int getItemCount()
    {
        return mistakeList.size();
    }

}
