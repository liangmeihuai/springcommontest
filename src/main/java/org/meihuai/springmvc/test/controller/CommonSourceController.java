package org.meihuai.springmvc.test.controller;

import org.meihuai.springmvc.test.aspect.MvcMethodLogAdvice;
import org.meihuai.springmvc.test.bean.Person;
import org.meihuai.springmvc.test.bean.Teacher;
import org.meihuai.springmvc.test.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private TeacherService teacherService;
    @Autowired
    private MvcMethodLogAdvice mvcMethodLogAdvice;
    private Logger logger = LoggerFactory.getLogger(CommonSourceController.class);
    @RequestMapping(value = "/test/first", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String,String> testFirst(){
        Map<String,String> map = new HashMap<String, String>();
        Teacher teacher = teacherService.selectByPrimaryKey(1);
        logger.info("print Logger info....................look print Logger infomation");
        map.put("code", "200");
        map.put("desc", "success");
        map.put("teacherName", teacher.getName());
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
