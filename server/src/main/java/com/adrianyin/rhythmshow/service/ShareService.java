package com.adrianyin.rhythmshow.service;

import com.adrianyin.rhythmshow.domain.Share;
import com.adrianyin.rhythmshow.repository.ShareRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ShareService {

    final ShareRepository shareRepository;

    @Autowired
    public ShareService(ShareRepository shareRepository) {
        this.shareRepository = shareRepository;
    }

    public Page<Share> getByTypeAndIsCollect(Collection<Integer> types,
                                             Collection<Integer> isOriginals,
                                             String sortByString,
                                             Integer pageNum,
                                             Integer pageSize) {
        // 分页和排序请求
        Pageable pageable;
        if (sortByString.equals("time")) {
            // 按时间降序排列
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("publishTime").descending());
        } else if (sortByString.equals("likeNum")) {
            // 按获赞数降序排列
            pageable = PageRequest.of(pageNum, pageSize, Sort.by("likeNum").descending());
        } else {
            pageable = PageRequest.of(pageNum, pageSize);
        }

        return shareRepository.findByTypeInAndIsOriginalInAndIsPublished(types, isOriginals, 1, pageable);
    }

    public Share getById(int shareId) {
        return shareRepository.getById(shareId);
    }

    public void likeAddOne(Share share) {
        share.setLikeNum(share.getLikeNum() + 1);
        shareRepository.save(share);
    }

    public void collectAddOne(Share share) {
        share.setCollectNum(share.getCollectNum() + 1);
        shareRepository.save(share);
    }
}