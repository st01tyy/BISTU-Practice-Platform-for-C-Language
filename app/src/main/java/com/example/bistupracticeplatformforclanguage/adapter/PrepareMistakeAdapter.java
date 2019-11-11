package com.example.bistupracticeplatformforclanguage.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bistupracticeplatformforclanguage.MistakeBookActivity;
import com.example.bistupracticeplatformforclanguage.PrepareMistakeBookActivity;
import com.example.bistupracticeplatformforclanguage.R;
import com.example.bistupracticeplatformforclanguage.module.Mistake;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

import org.litepal.LitePal;

import java.io.Serializable;
import java.util.List;

public class PrepareMistakeAdapter extends RecyclerView.Adapter<PrepareMistakeAdapter.ViewHolder>
{
    private PrepareMistakeBookActivity activity;
    private List<Object> mistakeList;

    public PrepareMistakeAdapter(PrepareMistakeBookActivity activity, List<Object> mistakeList)
    {
        this.activity = activity;
        this.mistakeList = mistakeList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView text_questionId;
        TextView text_description;
        Button btn_delete;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            text_questionId = (TextView) itemView.findViewById(R.id.text_item_questionId);
            text_description = (TextView) itemView.findViewById(R.id.text_item_description);
            btn_delete = (Button) itemView.findViewById(R.id.btn_delete);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prepare_mistake, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {
        Object o = mistakeList.get(position);
        if(o instanceof TrueFalseQuestion)
        {
            TrueFalseQuestion question = (TrueFalseQuestion) o;
            holder.text_questionId.setText(Integer.toString(question.getQuestionId()));
            holder.text_description.setText(question.getDescription());
        }
        else
        {
            MultipleChoiceQuestion question = (MultipleChoiceQuestion) o;
            holder.text_questionId.setText(Integer.toString(question.getQuestionId()));
            holder.text_description.setText(question.getDescription());
        }

        holder.text_description.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(activity, MistakeBookActivity.class);
                intent.putExtra("mistakeList", (Serializable) mistakeList);
                intent.putExtra("position", position);
                activity.startActivity(intent);
            }
        });

        holder.btn_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                try
                {
                    Object o = mistakeList.get(position);
                    if(o instanceof TrueFalseQuestion)
                    {
                        TrueFalseQuestion question = (TrueFalseQuestion) o;
                        LitePal.deleteAll(Mistake.class, "questionid=?", Integer.toString(question.getQuestionId()));
                    }
                    else
                    {
                        MultipleChoiceQuestion question = (MultipleChoiceQuestion) o;
                        LitePal.deleteAll(Mistake.class, "questionid=?", Integer.toString(question.getQuestionId()));
                    }
                    activity.refreshAdapter();
                }
                catch(Exception e)
                {
                    Log.e("PrepareMistakeAdapter", e.getMessage());
                    e.printStackTrace();
                    Toast.makeText(activity, "删除时出错", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mistakeList.size();
    }
}
