package com.minmin.wenda.dao;

import com.minmin.wenda.WendaApplication;
import com.minmin.wenda.model.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhoutianbin
 * Date: 2020-08-05
 * Time: 00:09
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
public class QuestionDAOTest {

    @Autowired
    private QuestionDAO questionDAO;

    @Test
    public void init_question() {
        for (int i = 0; i < 10; i++) {
            Question question = new Question();
            question.setCommentCount(i);
            Date date = new Date();
            date.setTime(date.getTime() + 1000*3600*i);
            question.setCreatedDate(date);
            question.setUserId(i+1);
            question.setTitle(String.format("TITLE{%d}", i));
            question.setContent(String.format("love minmin, hahahahh Content %d", i));
            questionDAO.addQuestion(question);
        }
    }

    @Test
    public void test_select_latest_question() {
        List<Question> questions = questionDAO.selectLatestQuestions(1, 0, 10);
        List<Question> questions2 = questionDAO.selectLatestQuestions(0, 0, 10);
        System.out.println(questions);
        System.out.println(questions2);
    }
}
