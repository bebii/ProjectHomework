package com.example.habebe.projecthomework.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.habebe.projecthomework.R;
import com.example.habebe.projecthomework.dao.homeworkset.Question;

import java.util.List;

public class DoExerciseAdapter extends BaseAdapter {

    List<Question> questions;
    Activity activity;

    public DoExerciseAdapter(List<Question> questions, Activity activity) {
        this.questions = questions;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return questions.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder = new CustomViewHolder();
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.listview_itemassign, null);

            holder.tvQuestion = (TextView) convertView.findViewById(R.id.tvQuestion);
            holder.radioGroup = (RadioGroup) convertView.findViewById(R.id.radioGroup);
            holder.radioA = (RadioButton) convertView.findViewById(R.id.radioA);
            holder.radioB = (RadioButton) convertView.findViewById(R.id.radioB);
            holder.radioC = (RadioButton) convertView.findViewById(R.id.radioC);
            holder.radioD = (RadioButton) convertView.findViewById(R.id.radioD);
            holder.radioE = (RadioButton) convertView.findViewById(R.id.radioE);

            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }

        holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radioA :
                        checkAnswer(position, 0);
                        break;
                    case R.id.radioB :
                        checkAnswer(position, 1);
                        break;
                    case R.id.radioC :
                        checkAnswer(position, 2);
                        break;
                    case R.id.radioD :
                        checkAnswer(position, 3);
                        break;
                    case R.id.radioE :
                        checkAnswer(position, 4);
                        break;
                }
            }
        });

        holder.tvQuestion.setText(questions.get(position).getProposition());
        holder.radioA.setText(questions.get(position).getAnswer().get(0).getText());
        holder.radioB.setText(questions.get(position).getAnswer().get(1).getText());
        holder.radioC.setText(questions.get(position).getAnswer().get(2).getText());
        holder.radioD.setText(questions.get(position).getAnswer().get(3).getText());
        holder.radioE.setText(questions.get(position).getAnswer().get(4).getText());

        return convertView;
    }

    private void checkAnswer(int position, int checkPosition) {
        if(Integer.valueOf(questions.get(position)
                .getAnswer().get(checkPosition).getCorrect()) == 1){
            questions.get(position).setScore(1);
        } else {
            questions.get(position).setScore(0);
        }
    }

    public List<Question> getQuestions(){
        return questions;
    }

    private class CustomViewHolder {
        TextView tvQuestion;
        RadioGroup radioGroup;
        RadioButton radioA, radioB, radioC, radioD, radioE;
    }
}
