package org.meihuai.springmvc.test.service.impl;

import org.meihuai.springmvc.test.bean.Teacher;
import org.meihuai.springmvc.test.mapper.TeacherMapper;
import org.meihuai.springmvc.test.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by tend on 2018/2/24.
 */
@Service
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private TeacherMapper teacherMapper;
    public Teacher selectByPrimaryKey(Integer id) {
        return teacherMapper.selectByPrimaryKey(id);
    }
}
