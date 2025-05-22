package com.kodvix.healthsafetyawareness.service;

import com.kodvix.healthsafetyawareness.entity.User;

import java.util.List;

public interface UserService {
	 public User createUser(User user);
	 public User getUserById(Long id);
	 public List<User> getAllUsers();
	 public User updateUser(Long id, User user);
	 public void deleteUser(Long id);
	 public User getUserByEmail(String email);

}