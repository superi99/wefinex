package com.scam.demo.repository;

import com.scam.demo.controller.MyUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepos extends CrudRepository<MyUser, Long> {
    MyUser findMyUserByEmail(String email);
}
