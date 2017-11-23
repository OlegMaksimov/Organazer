package com.example.controller;

import org.springframework.web.bind.annotation.*;

/**
 * Created by iMac on 23.11.2017.
 */
@RestController
@RequestMapping("/task")
public class TaskController {

    @GetMapping
    public String getTask() {
        return "this get mapping";
    }

    @PutMapping(value = "/{id}")
    public String readTask(@PathVariable Long id) {
        return "this put mapping" + id;
    }

    @PostMapping
    public String createTask() {
        return "this post mapping";
    }

    @DeleteMapping(value = "/{id}")
    public String deleteTask(@PathVariable Long id) {
        return "this delete mapping" + id;
    }

}
