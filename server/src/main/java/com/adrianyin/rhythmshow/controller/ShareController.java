package com.adrianyin.rhythmshow.controller;

import com.adrianyin.rhythmshow.config.CurrentUser;
import com.adrianyin.rhythmshow.config.IgnoreSecurity;
import com.adrianyin.rhythmshow.domain.Share;
import com.adrianyin.rhythmshow.domain.User;
import com.adrianyin.rhythmshow.result.Result;
import com.adrianyin.rhythmshow.result.ResultFactory;
import com.adrianyin.rhythmshow.service.CollectService;
import com.adrianyin.rhythmshow.service.LikeService;
import com.adrianyin.rhythmshow.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@Controller
public class ShareController {

    final ShareService shareService;
    final CollectService collectService;
    final LikeService likeService;

    @Autowired
    public ShareController(ShareService shareService, CollectService collectService, LikeService likeService) {
        this.shareService = shareService;
        this.collectService = collectService;
        this.likeService = likeService;
    }

    @IgnoreSecurity
    @PostMapping("/api/shares")
    @ResponseBody
    public Result getShares(@RequestBody Map<String, Object> requestBody) {

        @SuppressWarnings("unchecked")
        Collection<Integer> types = (Collection<Integer>) requestBody.get("types");
        @SuppressWarnings("unchecked")
        Collection<Integer> isOriginals = (Collection<Integer>) requestBody.get("isOriginals");
        String sortByString = requestBody.get("sortByString").toString();
        int pageNum = Integer.parseInt(requestBody.get("pageNum").toString());
        int pageSize = Integer.parseInt(requestBody.get("pageSize").toString());

        Page<Share> data = shareService.getByTypeAndIsCollect(types, isOriginals, sortByString, pageNum, pageSize);
        return ResultFactory.buildSuccessResult(data);
    }

    @PostMapping("/api/like")
    @ResponseBody
    public Result addLike(@RequestBody Map<String,Integer> requestBody, @CurrentUser User user) {
        int shareId = requestBody.get("shareId");
        Share share = shareService.getById(shareId);
        if (likeService.getByUserAndShare(user, share) != null) {
            return ResultFactory.buildFailResult("已经赞过该作品了");
        }
        likeService.addWithUserAndShare(user, share);
        return ResultFactory.buildSuccessResult("点赞成功");
    }

    @PostMapping("/api/collect")
    @ResponseBody
    public Result addCollect(@RequestBody Map<String,Integer> requestBody, @CurrentUser User user) {
        int shareId = requestBody.get("shareId");
        Share share = shareService.getById(shareId);
        if (collectService.getByUserAndShare(user, share) != null) {
            return ResultFactory.buildFailResult("已经收藏过该作品了");
        }
        collectService.addWithUserAndShare(user, share);
        return ResultFactory.buildSuccessResult("收藏成功");
    }
}
