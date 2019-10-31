package com.example.bistupracticeplatformforclanguage.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bistupracticeplatformforclanguage.PracticeActivity;
import com.example.bistupracticeplatformforclanguage.R;
import com.example.bistupracticeplatformforclanguage.module.MultipleChoiceQuestion;

public class SelectionFragment extends Fragment implements CompoundButton.OnCheckedChangeListener
{
    private MultipleChoiceQuestion multipleChoiceQuestion;
    private PracticeActivity practiceActivity;

    private CheckBox check_selectionA, check_selectionB, check_selectionC, check_selectionD;
    private TextView text_selectionA, text_selectionB, text_selectionC, text_selectionD;

    public SelectionFragment(MultipleChoiceQuestion multipleChoiceQuestion, PracticeActivity practiceActivity)
    {
        super();
        this.multipleChoiceQuestion = multipleChoiceQuestion;
        this.practiceActivity = practiceActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_selection, container, false);
        updateUI(view);
        return view;
    }

    private void updateUI(View view)
    {
        //获取控件
        check_selectionA = (CheckBox) view.findViewById(R.id.check_selection_a);
        check_selectionB = (CheckBox) view.findViewById(R.id.check_selection_b);
        check_selectionC = (CheckBox) view.findViewById(R.id.check_selection_c);
        check_selectionD = (CheckBox) view.findViewById(R.id.check_selection_d);

        text_selectionA = (TextView) view.findViewById(R.id.text_selection_a);
        text_selectionB = (TextView) view.findViewById(R.id.text_selection_b);
        text_selectionC = (TextView) view.findViewById(R.id.text_selection_c);
        text_selectionD = (TextView) view.findViewById(R.id.text_selection_d);

        //设置控件
        check_selectionA.setOnCheckedChangeListener(this);
        check_selectionB.setOnCheckedChangeListener(this);
        check_selectionC.setOnCheckedChangeListener(this);
        check_selectionD.setOnCheckedChangeListener(this);

        text_selectionA.setText(multipleChoiceQuestion.getSelectionA());
        text_selectionB.setText(multipleChoiceQuestion.getSelectionB());
        text_selectionC.setText(multipleChoiceQuestion.getSelectionC());
        text_selectionD.setText(multipleChoiceQuestion.getSelectionD());
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b)
    {
        if(b)
        {
            if(check_selectionA.getId() == compoundButton.getId())
                practiceActivity.updateAnswer("A");
            else
                check_selectionA.setChecked(false);
            if(check_selectionB.getId() == compoundButton.getId())
                practiceActivity.updateAnswer("B");
            else
                check_selectionB.setChecked(false);
            if(check_selectionC.getId() == compoundButton.getId())
                practiceActivity.updateAnswer("C");
            else
                check_selectionC.setChecked(false);
            if(check_selectionD.getId() == compoundButton.getId())
                practiceActivity.updateAnswer("D");
            else
                check_selectionD.setChecked(false);
        }
        else
        {
            if(!check_selectionA.isChecked() && !check_selectionB.isChecked() && !check_selectionC.isChecked() && !check_selectionD.isChecked())
                practiceActivity.updateAnswer(null);
        }
    }
}
