package com.qing.simpleframe.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qing.simpleframe.domain.entity.User;
import com.qing.simpleframe.repository.DemoRepository;

@Service
@Transactional
public class DemoService {

    @Autowired
	DemoRepository demoDao;
	
	public User findByName(String name) { 
        return demoDao.findByName(name);
    } 
	
	public void saveUser(User user){
	    demoDao.insertOne(user);
	}
}
