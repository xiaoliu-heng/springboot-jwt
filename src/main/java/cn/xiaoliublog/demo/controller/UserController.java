package cn.xiaoliublog.demo.controller;

import cn.xiaoliublog.demo.model.User;
import cn.xiaoliublog.demo.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/login")
    public String login(@NonNull @RequestBody User user){
        if (user.getUsername().equals("test") && user.getPassword().equals("test")){
            return jwtUtils.getToken(user.getUsername(), user.getPassword());
        }
        return "";
    }

    @GetMapping("/hello")
    public String hello(@RequestAttribute("username") String name){
        return "Hello "+name;
    }
}
