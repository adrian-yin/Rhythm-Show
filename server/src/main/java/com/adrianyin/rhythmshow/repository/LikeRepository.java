package com.adrianyin.rhythmshow.repository;

import com.adrianyin.rhythmshow.domain.Like;
import com.adrianyin.rhythmshow.domain.Share;
import com.adrianyin.rhythmshow.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface LikeRepository extends CrudRepository<Like, Integer> {
    Like getByUserAndShare(User user, Share share);

    @Modifying
    @Transactional
    void deleteByShare(Share share);
}
