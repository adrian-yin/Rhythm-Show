package com.adrianyin.rhythmshow.service;

import com.adrianyin.rhythmshow.domain.User;
import com.adrianyin.rhythmshow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByEmailAndPassword(String email, String password) {
        return userRepository.getByEmailAndPassword(email, password);
    }

    public boolean emailHasBeenRegistered(String email) {
        User user = userRepository.getByEmail(email);
        return user != null;
    }

    public void addNormalUserWithEmailAndPassword(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setNickname(email);
        user.setPassword(password);
        user.setRole(0);

        userRepository.save(user);
    }
}
