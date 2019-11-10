package com.example.bistupracticeplatformforclanguage.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bistupracticeplatformforclanguage.R;

import java.util.List;

public class FinishTestKnowledgeAdapter extends RecyclerView.Adapter<FinishTestKnowledgeAdapter.ViewHolder>
{
    private List<String> knowledgeList;

    public FinishTestKnowledgeAdapter(List<String> knowledgeList)
    {
        super();
        this.knowledgeList = knowledgeList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView text_itemKnowledge;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            text_itemKnowledge = itemView.findViewById(R.id.text_item_knowledge);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_knowledge, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        holder.text_itemKnowledge.setText(knowledgeList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return knowledgeList.size();
    }
}
