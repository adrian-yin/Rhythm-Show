package com.adrianyin.rhythmshow.service;

import com.adrianyin.rhythmshow.domain.Collect;
import com.adrianyin.rhythmshow.domain.Like;
import com.adrianyin.rhythmshow.domain.Share;
import com.adrianyin.rhythmshow.domain.User;
import com.adrianyin.rhythmshow.repository.CollectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CollectService {

    final CollectRepository collectRepository;
    final ShareService shareService;

    @Autowired
    public CollectService(CollectRepository collectRepository, ShareService shareService) {
        this.collectRepository = collectRepository;
        this.shareService = shareService;
    }

    public Collect getByUserAndShare(User user, Share share) {
        return collectRepository.getByUserAndShare(user, share);
    }

    public void addWithUserAndShare(User user, Share share) {
        Collect collect = new Collect();
        collect.setUser(user);
        collect.setShare(share);
        collectRepository.save(collect);

        // share记录表中对应点赞数加1
        shareService.collectAddOne(share);
    }

    public List<Share> getShareByUser(User user) {
        List<Collect> collects = collectRepository.getByUser(user);
        List<Share> result = new ArrayList<>();
        for (Collect collect : collects) {
            result.add(collect.getShare());
        }
        return result;
    }

    public void deleteByShareAndUser(Share share, User user) {
        collectRepository.deleteByShareAndUser(share, user);
    }

    public void deleteByShare(Share share) {
        collectRepository.deleteByShare(share);
    }
}
