package com.example.bistupracticeplatformforclanguage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bistupracticeplatformforclanguage.Function;
import com.example.bistupracticeplatformforclanguage.PrepareTestActivity;
import com.example.bistupracticeplatformforclanguage.R;
import com.example.bistupracticeplatformforclanguage.adapter.PretestStageAdapter;
import com.example.bistupracticeplatformforclanguage.module.Stage;

import java.util.List;

public class StagePretestFragment extends Fragment
{
    private PrepareTestActivity activity;

    private RecyclerView pretestStage;

    public StagePretestFragment(PrepareTestActivity activity)
    {
        super();
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_stage, container, false);
        pretestStage = (RecyclerView) view.findViewById(R.id.list_pretest_stage);
        initialize();
        return view;
    }

    private void initialize()
    {
        List<String> stageList = Function.findStage();
        PretestStageAdapter adapter = new PretestStageAdapter(activity, stageList);
        LinearLayoutManager manager = new LinearLayoutManager(activity);
        manager.setOrientation(RecyclerView.VERTICAL);
        pretestStage.setLayoutManager(manager);
        pretestStage.setAdapter(adapter);
    }
}
