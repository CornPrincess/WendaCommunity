package com.minmin.wenda.controller;

import com.minmin.wenda.service.WendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author corn
 * @version 1.0.0
 */

@Controller
public class SettingController {
    @Autowired
    WendaService wendaService;

    @RequestMapping(path = {"/setting"})
    @ResponseBody
    public String index() {

        return "hello " + wendaService.getMessage(1);
    }
}
