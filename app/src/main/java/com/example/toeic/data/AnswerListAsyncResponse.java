package com.example.toeic.data;

import com.example.toeic.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
     void processFinished(ArrayList<Question> questionArrayList);
}
