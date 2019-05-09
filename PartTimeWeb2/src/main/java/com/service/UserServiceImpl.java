package com.service;

import com.dao.UserDaoImpl;
import com.entity.User;
import org.springframework.stereotype.Service;

@Service
public  class UserServiceImpl implements UserService{
    public UserDaoImpl dao;
    @Override
    public boolean getUser(User user) {
        return dao.getUser(user);
    }
}
