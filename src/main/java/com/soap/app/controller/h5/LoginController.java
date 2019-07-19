package com.soap.app.controller.h5;

import com.soap.app.entity.ResponseEntity;
import com.soap.app.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@Controller
@RequestMapping("/h5")
public class LoginController {

    @GetMapping("/login")
    @ResponseBody
    @CrossOrigin
    public ResponseEntity AppLogin(@RequestParam("username") String username,
                                   @RequestParam("password") String password) {
        User user = new User();
        user.setName(username);
        user.setPassword(password);
        return ResponseEntity.ok(user);
    }

    public static void main(String[] args) {
        if(3>2){
            System.out.println("haha1");
        }else if(4>2){
            System.out.println("hah3");
        }
    }
}
