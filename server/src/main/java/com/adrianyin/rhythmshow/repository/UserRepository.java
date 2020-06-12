package com.adrianyin.rhythmshow.repository;

import com.adrianyin.rhythmshow.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User getByEmailAndPassword(String email, String password);
    User getByEmail(String email);
}
