package com.adrianyin.rhythmshow.service;

import com.adrianyin.rhythmshow.domain.User;
import com.adrianyin.rhythmshow.repository.UserRepository;
import com.adrianyin.rhythmshow.utils.JwtUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByEmail(String email) {
        return userRepository.getByEmail(email);
    }

    public User getByEmailAndPassword(String email, String password) {
        return userRepository.getByEmailAndPassword(email, password);
    }

    public User getById(int id) {
        return userRepository.getById(id);
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

    public User getUserByToken(String token) {
        int userId = JwtUtils.getIdByToken(token);
        return this.getById(userId);
    }

    public User getAdminByEmail(String email) {
        return userRepository.getByEmailAndRole(email, 1);
    }
}
