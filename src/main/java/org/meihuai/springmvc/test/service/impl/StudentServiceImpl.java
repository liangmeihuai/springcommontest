package org.meihuai.springmvc.test.service.impl;

import org.meihuai.springmvc.test.bean.Student;
import org.meihuai.springmvc.test.bean.Teacher;
import org.meihuai.springmvc.test.mapper.StudentMapper;
import org.meihuai.springmvc.test.mapper.TeacherMapper;
import org.meihuai.springmvc.test.service.StudentService;
import org.meihuai.springmvc.test.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by tend on 2018/2/24.
 */
@Service
public class StudentServiceImpl implements StudentService{
    @Autowired
    private StudentMapper studentMapper;
    public Student selectByPrimaryKey(Integer id) {
        return studentMapper.selectByPrimaryKey(id);
    }
    @Transactional
    public int insertSelective(Student record) throws Exception {
        int i =  studentMapper.insertSelective(record);
        System.out.println("insert into student is i-student = " + i);
//        throw new Exception("ddddd-student");
        return i;
    }
}
