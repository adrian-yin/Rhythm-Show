package com.adrianyin.rhythmshow.service;

import com.adrianyin.rhythmshow.domain.Like;
import com.adrianyin.rhythmshow.domain.Share;
import com.adrianyin.rhythmshow.domain.User;
import com.adrianyin.rhythmshow.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    final LikeRepository likeRepository;
    final ShareService shareService;

    @Autowired
    public LikeService(LikeRepository likeRepository, ShareService shareService) {
        this.likeRepository = likeRepository;
        this.shareService = shareService;
    }

    public Like getByUserAndShare(User user, Share share) {
        return likeRepository.getByUserAndShare(user, share);
    }

    public void addWithUserAndShare(User user, Share share) {
        Like like = new Like();
        like.setUser(user);
        like.setShare(share);
        likeRepository.save(like);

        // share记录表中对应点赞数加1
        shareService.likeAddOne(share);
    }

    public void deleteByShare(Share share) {
        likeRepository.deleteByShare(share);
    }
}
