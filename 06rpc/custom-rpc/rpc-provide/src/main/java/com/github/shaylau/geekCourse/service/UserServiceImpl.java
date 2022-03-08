package com.github.shaylau.geekCourse.service;


import com.github.shaylau.geekCourse.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User findById(int id) {
        return new User(id, "User" + System.currentTimeMillis());
    }
}
