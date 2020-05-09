package cn.xiaoliublog.demo.controller;

import cn.xiaoliublog.demo.model.User;
import cn.xiaoliublog.demo.utils.JWTUtils;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
public class UserController {
    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/login")
    public String login(@NonNull @RequestBody User user, HttpServletResponse resp){
        if (user.getUsername().equals("test") && user.getPassword().equals("test")){
            val token = jwtUtils.getToken(user.getUsername(), user.getPassword());
            resp.addCookie(new Cookie("authorization", token));
            return token;
        }
        return "";
    }

    @GetMapping("/hello")
    public String hello(@RequestAttribute("username") String name){
        return "Hello "+name;
    }
}
