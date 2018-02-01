package org.meihuai.springmvc.test.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tend on 2018/2/1.
 */
@RestController
public class CommonSourceController {
    @RequestMapping(value = "/test/first", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Map<String,String> testFirst(){
        Map<String,String> map = new HashMap<String, String>();
        map.put("code", "200");
        map.put("desc", "success");
        return map;
    }

}
