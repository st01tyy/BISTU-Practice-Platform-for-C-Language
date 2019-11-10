package com.example.bistupracticeplatformforclanguage.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bistupracticeplatformforclanguage.AnswerQuestionActivity;
import com.example.bistupracticeplatformforclanguage.PracticeActivity;
import com.example.bistupracticeplatformforclanguage.R;
import com.example.bistupracticeplatformforclanguage.module.TrueFalseQuestion;

public class TrueFalseFragment extends Fragment implements CompoundButton.OnCheckedChangeListener
{
    private AnswerQuestionActivity activity;

    private CheckBox check_true, check_false;

    public TrueFalseFragment(AnswerQuestionActivity activity)
    {
        super();
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_true_false, container, false);
        check_true = (CheckBox) view.findViewById(R.id.check_true);
        check_false = (CheckBox) view.findViewById(R.id.check_false);

       String ans = activity.getSelectedAnswers();
       if(ans != null)
       {
           if(ans.equals("0"))
               check_false.setChecked(true);
           else
               check_true.setChecked(true);
       }

        check_true.setOnCheckedChangeListener(this);
        check_false.setOnCheckedChangeListener(this);
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        if(b)
        {
            if(compoundButton.getId() == check_true.getId())
                activity.updateAnswer("1");
            else
                check_true.setChecked(false);
            if(compoundButton.getId() == check_false.getId())
                activity.updateAnswer("0");
            else
                check_false.setChecked(false);
        }
        else
        {
            if(!check_true.isChecked() && !check_false.isChecked())
                activity.updateAnswer(null);
        }
    }
}
