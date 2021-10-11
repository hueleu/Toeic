package com.example.toeic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.toeic.data.Repository;
import com.example.toeic.databinding.ActivityMainBinding;
import com.example.toeic.model.Question;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private int currentQuestionIndex = 1;
    List<Question> questionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Log.d("hue.leu", "start");
        questionsList = new Repository().getQuestions(questionArrayList -> {
            binding.questionTextview.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
            binding.textViewOutOf.setText("Question: " +currentQuestionIndex +"/"+questionsList.size());
        });

        binding.buttonNext.setOnClickListener(this::onClick);
        binding.buttonTrue.setOnClickListener(this::onClick);
        binding.buttonFalse.setOnClickListener(this::onClick);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.button_false:
                checkAnswer(false);
                updateQuestion();
                break;
            case R.id.button_true:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.button_next:
                currentQuestionIndex = (currentQuestionIndex +1)%questionsList.size();
                updateQuestion();
                break;
        }
    }

    private void checkAnswer(boolean b) {
        boolean answer = questionsList.get(currentQuestionIndex).isAnswerTrue();
        int snackMessageId;
        if(b == answer){
            snackMessageId = R.string.correct_answer;
            fadeAnimation();
        } else {
            snackMessageId = R.string.incorrect_answer;
            shakeAnimation();
        }
       Snackbar.make(binding.cardView, snackMessageId, Snackbar.LENGTH_SHORT).show();
    }

    private void updateQuestion() {
        String question = questionsList.get(currentQuestionIndex).getAnswer();
        binding.questionTextview.setText(question);
        binding.textViewOutOf.setText("Question: " +currentQuestionIndex +"/"+questionsList.size());
    }

    private void fadeAnimation(){
        AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
        animation.setDuration(300);
        animation.setRepeatCount(1);
        animation.setRepeatMode(Animation.REVERSE);

        binding.cardView.setAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextview.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextview.setTextColor(Color.WHITE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void shakeAnimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this, R.anim.shake_animation);
        binding.cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                binding.questionTextview.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                binding.questionTextview.setTextColor(Color.WHITE);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}