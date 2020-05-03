package cn.xiaoliublog.demo.controller;

import cn.xiaoliublog.demo.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/login")
    public String login(String username, String password){
        if (username.equals("test") && password.equals("test")){
            return jwtUtils.getToken(username, password);
        }
        return "";
    }

    @GetMapping("/hello")
    public String hello(@RequestAttribute("username") String name){
        return "Hello "+name;
    }
}
