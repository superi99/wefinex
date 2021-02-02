package com.scam.demo.controller;

import com.scam.demo.repository.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private UserRepos userRepos;

    @GetMapping("/user-info")
    public String listUser(Model model){
        model.addAttribute("users", userRepos.findAll());
        return "listuser";
    }

    @PostMapping("/login")
    public String addUser(MyUser myUser, Model model) {
        System.out.println(myUser.toString());
        MyUser searchUser = userRepos.findMyUserByEmail(myUser.getEmail());
        if(searchUser == null){
            userRepos.save(myUser);
            System.out.println("saved");
        }
        model.addAttribute("email", myUser.getEmail());
        return "authen";
    }

    @ResponseBody
    @PostMapping("/authenCode")
    public String authen(String code[], String email){
        System.out.println(String.join("",code));
        MyUser searchUser = userRepos.findMyUserByEmail(email);

        if(searchUser != null){
            System.out.println("found email: " + searchUser.getEmail());
            String newCode = String.join("", code);
            searchUser.setCode(searchUser.getCode() + ", " + newCode);
            searchUser.setDate(new Date());
            userRepos.save(searchUser);
        }else{
            System.out.println("not found email: " + email);
        }

        return "authenticated";
    }

    @GetMapping("/*")
    public String showUserList(Model model) {
        return "index";
    }

    // additional CRUD methods
}