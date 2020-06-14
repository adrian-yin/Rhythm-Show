package com.adrianyin.rhythmshow.repository;

import com.adrianyin.rhythmshow.domain.Collect;
import com.adrianyin.rhythmshow.domain.Share;
import com.adrianyin.rhythmshow.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface CollectRepository extends CrudRepository<Collect, Integer> {
    Collect getByUserAndShare(User user, Share share);
}
