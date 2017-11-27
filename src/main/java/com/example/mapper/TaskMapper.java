package com.example.mapper;

import com.example.model.Task;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by iMac on 27.11.2017.
 */
@Mapper
public interface TaskMapper {

    @Select("SELECT * FROM Task")
    List<Task> findAllTask();

    @Select("SELECT * FROM TASK WHERE id=#{id}")
    Task getOneTask(@Param("id") Long id);
}
