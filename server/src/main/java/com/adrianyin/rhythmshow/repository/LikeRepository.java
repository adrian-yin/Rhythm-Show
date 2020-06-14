package com.adrianyin.rhythmshow.repository;

import com.adrianyin.rhythmshow.domain.Like;
import com.adrianyin.rhythmshow.domain.Share;
import com.adrianyin.rhythmshow.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface LikeRepository extends CrudRepository<Like, Integer> {
    Like getByUserAndShare(User user, Share share);
}
