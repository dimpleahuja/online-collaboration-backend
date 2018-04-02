package com.niit.dao;

import com.niit.model.User;

public interface UserDao {
void registerUser(User user);
boolean isEmailUnique(String email);
User login(User user);// return either 1 user object or null value
void update(User validUser);
User getUser(String email);
void updateUser(User user);
}
