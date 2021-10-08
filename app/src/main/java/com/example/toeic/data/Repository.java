package com.example.toeic.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.toeic.controller.AppController;
import com.example.toeic.model.Question;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    ArrayList<Question> questions = new ArrayList<>();
    String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            for (int i = 0; i < response.length() ; i++) {
                try {
                    if(i<10) Log.d("hue.leu","i "+i);
                    Question question = new Question(response.getJSONArray(i).get(0).toString(), response.getJSONArray(i).getBoolean(1));
                    questions.add(question);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            if(null != callBack){
                Log.d("hue.leu", "callback "+callBack);
                callBack.processFinished(questions);
            }

        }, error -> {

        });
        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questions;
    }

}
