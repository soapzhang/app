package com.soap.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class HelloController {
    private static final Logger LOGGER = LoggerFactory.getLogger("app");
    /**
     * hello spring boot
     *
     * @return
     */
    @GetMapping
    public String hello(Model model) {
        LOGGER.warn("hello world!");
        model.addAttribute("hello","welcome to my hello page!");
        return "hello";
    }
}
