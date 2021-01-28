package com.scam.demo.controller;

import com.scam.demo.repository.UserRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

    @PostMapping("/authenCode")
    public String authen(String code[], String email, Model model){
        System.out.println(String.join("",code));
        MyUser searchUser = userRepos.findMyUserByEmail(email);

        if(searchUser != null){
            System.out.println("found email: " + searchUser.getEmail());
            searchUser.setCode(String.join("",code));
            searchUser.setDate(new Date());
            userRepos.save(searchUser);
        }
        System.out.println("not found email");
        return "authen";
    }

    @GetMapping("/")
    public String showUserList(Model model) {
        return "index";
    }

    // additional CRUD methods
}