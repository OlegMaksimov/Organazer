package com.example.controller;

import com.example.model.Task;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by iMac on 23.11.2017.
 */
@RestController
@RequestMapping("/")
public class IndexController {

@GetMapping()
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
