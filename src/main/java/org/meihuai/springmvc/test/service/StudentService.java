package org.meihuai.springmvc.test.service;

import org.meihuai.springmvc.test.bean.Student;
import org.meihuai.springmvc.test.bean.Teacher;

/**
 * Created by tend on 2018/2/24.
 */
public interface StudentService {
    Student selectByPrimaryKey(Integer id);
    int insertSelective(Student record) throws Exception;
}
