package com.minmin.wenda.service;

import com.minmin.wenda.dao.QuestionDAO;
import com.minmin.wenda.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author corn
 * @version 1.0.0
 */

@Service
public class QuestionService {
    @Autowired
    QuestionDAO questionDAO;


    public int addQuestion(Question question) {
        // TODO 敏感词过滤
        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0;
    }

    /*
        select LatestQuestions service (desc order)
     */
    public List<Question> getLatestQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }
}
