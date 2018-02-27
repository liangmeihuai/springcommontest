package org.meihuai.springmvc.test.service.impl;

import org.meihuai.springmvc.test.bean.Student;
import org.meihuai.springmvc.test.bean.Teacher;
import org.meihuai.springmvc.test.mapper.TeacherMapper;
import org.meihuai.springmvc.test.service.StudentService;
import org.meihuai.springmvc.test.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * Created by tend on 2018/2/24.
 */
@Service
public class TeacherServiceImpl implements TeacherService{
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private StudentService studentService;
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
//    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Teacher selectByPrimaryKey(Integer id) {
        Teacher teacher = teacherMapper.selectByPrimaryKey(id);
        System.out.println("teacher 1 ................dirt... = " + teacher.getName());

        Teacher teacher2 = teacherMapper.selectByPrimaryKey(id);
        System.out.println("teacher 2 ................dirt... = " + teacher2.getName());
        return teacher2;
    }
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class})
    public int insertSelective(Teacher record) throws Exception {
        int i =  teacherMapper.insertSelective(record);
        Student student = new Student();
        student.setName("luke-daughter");
        studentService.insertSelective(student);
        System.out.println("insert into teacher is i-teacher = " + i);
        throw new Exception("ddddd");
    }
}
