package org.meihuai.springmvc.test.service;

import org.meihuai.springmvc.test.bean.Teacher;

/**
 * Created by tend on 2018/2/24.
 */
public interface TeacherService {
    Teacher selectByPrimaryKey(Integer id);
    int insertSelective(Teacher record) throws Exception;
}
