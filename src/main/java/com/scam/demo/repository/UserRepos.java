package com.scam.demo.repository;

import com.scam.demo.controller.MyUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepos extends CrudRepository<MyUser, Long> {
    MyUser findMyUserByEmail(String email);
    List<MyUser> findAllByOrderByDateDesc();
}
