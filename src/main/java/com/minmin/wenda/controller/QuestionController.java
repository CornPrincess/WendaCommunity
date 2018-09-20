package com.minmin.wenda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author corn
 * @version 1.0.0
 */
public class QuestionController {
    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);

    @RequestMapping(value = "/question/add", method = {RequestMethod.POST})
    @RequestBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {
        try {

        } catch (Exception e) {
            logger.error("增加题目失败" + e.getMessage());
        }

        // TODO AJAX
        // return json string
        // use fastjson dependencies
        return "{'code':0,'content':'minmin'}";
    }
}
