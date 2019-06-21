package com.soap.app.controller;

import com.soap.app.entity.User;
import com.soap.app.mapper.UserDao;
import com.soap.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.LongAdder;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseBody
    public String addUser(User user) {
        user.setSex(1);
        user.setDob(new Date());
        user.setAddress("soap address");
        userService.saveUser(user);
        return "OK";
    }

    public static void main(String[] args) {
        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        for (String temp : a) {
            if ("2".equals(temp)) {
                a.remove(temp);
            }
        }
        System.out.println(a);
        LongAdder
    }

}
