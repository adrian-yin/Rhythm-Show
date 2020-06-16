package com.adrianyin.rhythmshow.repository;

import com.adrianyin.rhythmshow.domain.Collect;
import com.adrianyin.rhythmshow.domain.Share;
import com.adrianyin.rhythmshow.domain.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface CollectRepository extends CrudRepository<Collect, Integer> {
    Collect getByUserAndShare(User user, Share share);

    List<Collect> getByUser(User user);

    @Modifying
    @Transactional
    void deleteByShareAndUser(Share share, User user);

    @Modifying
    @Transactional
    void deleteByShare(Share share);
}
