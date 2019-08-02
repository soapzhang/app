package com.soap.app.controller;

import com.soap.app.entity.User;
import com.soap.app.service.UserServiceA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceA userService;

    @PostMapping
    public String addUser(User user) {
        user.setSex(1);
        user.setDob(new Date());
        user.setAddress("soap address");
        userService.saveUser(user);
        return "stat";
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
    }

}
