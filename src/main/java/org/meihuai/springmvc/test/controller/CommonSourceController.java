package org.meihuai.springmvc.test.controller;

import org.meihuai.springmvc.test.aspect.MvcMethodLogAdvice;
import org.meihuai.springmvc.test.bean.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tend on 2018/2/1.
 */
@RestController
public class CommonSourceController {
    @Autowired
    private MvcMethodLogAdvice mvcMethodLogAdvice;
    @RequestMapping(value = "/test/first", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String,String> testFirst(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("code", "200");
        map.put("desc", "success");
        return map;
    }
    @RequestMapping(value = "/test/exception", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String,String> testException(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("code", "200");
        map.put("desc", "success");
        int i = 1 / 0;
        return map;
    }

    @RequestMapping(value = "/test/bean", method = {RequestMethod.POST})
    public Map<String,String> testBean(@RequestBody Person person){
        Map<String,String> map = new HashMap<String, String>();
        map.put("code", "200");
        map.put("desc", person.getName());
        return map;
    }

}
