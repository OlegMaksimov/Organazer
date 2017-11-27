package com.example.controller;

import com.example.mapper.TaskMapper;
import com.example.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.List;

/**
 * Created by iMac on 23.11.2017.
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskMapper taskMapper;

    @GetMapping
    public List<Task> getAllTask() {
        return taskMapper.findAllTask();
    }

    @PutMapping(value = "/{id}")
    public ModelAndView readTask(@PathVariable Long id) {

        taskMapper.getOneTask(id);
        return new ModelAndView("task"); // TODO: 27.11.2017 добавить модель 
    }

    @PostMapping
    public Task createTask() {
        return  new Task();
    }

    @DeleteMapping(value = "/{id}")
    public String deleteTask(@PathVariable Long id) {
        return "this delete mapping" + id;
    }

}
