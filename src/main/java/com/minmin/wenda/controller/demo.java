package com.minmin.wenda.controller;

import com.minmin.wenda.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author corn
 * @version 1.0.0
 */

@Controller
@RequestMapping("/demo")
public class demo {

    /*
        value， method；
        value：   指定请求的实际地址，指定的地址可以是URI Template 模式（后面将会说明）；
        method：  指定请求的method类型， PUT、GET、DELETE、POST 分别对应注解@PutMapping @GetMapping @DeleteMapping @PostMapping；
                  没有默认值，如果不配置method，则以任何请求形式

        consumes，produces；
        consumes： 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
        produces:    指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；

        params，headers；
        params： 指定request中必须包含某些参数值是，才让该方法处理。
        headers： 指定request中必须包含某些指定的header值，才能让该方法处理请求。
     */
    @RequestMapping(value = "/method0")
    @ResponseBody
    public String method0() {
        return "method0";
    }


    @RequestMapping(value = {"/method1", "/method1/second"})
    @ResponseBody
    public String method1() {
        return "method1";
    }

    @RequestMapping(value = "/method2", method = RequestMethod.POST)
    @ResponseBody
    public String method2() {
        return "method2";
    }

    @RequestMapping(value = "/method3", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public String method3() {
        return "method3";
    }


    @RequestMapping(value = "/method4", headers = "name=pankaj")
    @ResponseBody
    public String method4() {
        return "method4";
    }

    @RequestMapping(value = "/method5", headers = {"name=pankaj", "id=1"})
    @ResponseBody
    public String method5() {
        return "method5";
    }

    @RequestMapping(value = "/method6", produces = {"application/json", "application/xml"}, consumes = "text/html")
    @ResponseBody
    public String method6() {
        return "method6";
    }

    @RequestMapping(value = "/method7/{id}")
    @ResponseBody
    public String method7(@PathVariable("id") int id) {
        return "method7 with id =" + id;
    }

    @RequestMapping(value = "/method8/{id:[\\d]+}/{name}")
    @ResponseBody
    public String method8(@PathVariable("id") long id, @PathVariable("name") String name) {
        return "method8 with id = " + id + " and name =" + name;
    }

    @RequestMapping(value = "/method9")
    @ResponseBody
    public String method9(@RequestParam("id") int id) {
        return "method9 with id = " + id;
    }

    @RequestMapping(path = {"/velocity"}, method = RequestMethod.GET)
    public String template(Model model) {
        // add String
        model.addAttribute("value1", "v11");

        // add list
        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
        model.addAttribute("colors", colors);

        // add map
        Map<String, String> map = new HashMap();
        for (int i = 0; i < 4; i++) {
            map.put(String.valueOf(i), String.valueOf(i * i));
        }
        model.addAttribute("map", map);

        // add class
        model.addAttribute("User", new User("minmin"));

        return "demo";
    }

    @RequestMapping()
    @ResponseBody
    public String defaultMethod() {
        return "default method";
    }

    @RequestMapping("*")
    @ResponseBody
    public String fallbackMethod() {
        return "fallback method";
    }

}
