package com.example.controller;

import com.example.model.Task;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

/**
 * Created by iMac on 23.11.2017.
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @GetMapping
    public List<Task> getTask() {
        Task task = new Task();
        List<Task> list = Arrays.asList(task);
        return list;
    }

    @PutMapping(value = "/{id}")
    public String readTask(@PathVariable Long id) {
        return "this put mapping" + id;
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
