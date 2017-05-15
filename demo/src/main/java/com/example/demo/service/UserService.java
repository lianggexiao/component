package com.example.demo.service;

import com.example.demo.domain.User;
import org.springframework.stereotype.Service;

/**
 * Created by liuq on 2017/5/15.
 */
@Service
public class UserService {

    public User getUser(String id) {

        return new User();
    }

    public void update(User u) {

    }

    public void remove(String id) {

    }
}
