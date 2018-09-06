package com.minmin.wenda.controller;

import com.minmin.wenda.model.Question;
import com.minmin.wenda.model.ViewObject;
import com.minmin.wenda.service.QuestionService;
import com.minmin.wenda.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


/**
 * @author corn
 * @version 1.0.0
 */

@Controller
public class HomeController {
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;


    @RequestMapping(path={"/", "/index"}, method = {RequestMethod.GET})
    public String index(Model model) {
        List<Question> questionList = questionService.getLatestQuestions(0, 0, 10);
        List<ViewObject> vos = new ArrayList<>();

        for(Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question",question);
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }

        model.addAttribute("vos", vos);

        return "index";
    }
}
