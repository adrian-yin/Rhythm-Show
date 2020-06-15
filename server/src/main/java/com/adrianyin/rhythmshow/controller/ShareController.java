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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
    public Result addCollect(@RequestBody Map<String, Integer> requestBody, @CurrentUser User user) {
        int shareId = requestBody.get("shareId");
        Share share = shareService.getById(shareId);
        if (collectService.getByUserAndShare(user, share) != null) {
            return ResultFactory.buildFailResult("已经收藏过该作品了");
        }
        collectService.addWithUserAndShare(user, share);
        return ResultFactory.buildSuccessResult("收藏成功");
    }

    @PostMapping("/api/addshare")
    @ResponseBody
    public Result addShare(@RequestBody Share share, @CurrentUser User user) {
        share.setUser(user);
        shareService.addNewShare(share);
        return ResultFactory.buildSuccessResult(share);
    }

    @PostMapping("/api/saverecord")
    @ResponseBody
    public Result addWavBob(@RequestParam(name = "wavBlob") MultipartFile wavBlob,
                           @RequestParam(name = "shareId") int shareId,
                           @CurrentUser User user) throws IOException {
        File file = new File("F:/Projects/Rhythm-Show/resources/records/record_" +
                Integer.toString(shareId) + ".wav");
        if (!wavBlob.isEmpty()) {
            wavBlob.transferTo(file);
        }
        return ResultFactory.buildSuccessResult("添加成功");
    }

    // 根据分享id获取录音
    @IgnoreSecurity
    @GetMapping("/api/getrecord")
    @ResponseBody
    public void recordFile(@RequestParam(name = "shareId") int shareId,
                           HttpServletResponse response) throws IOException {
        FileInputStream inputStream = new FileInputStream("F:/Projects/Rhythm-Show/resources/records/record_" +
                Integer.toString(shareId) + ".wav");
        int i = inputStream.available();
        byte[] buff = new byte[i];
        inputStream.read(buff);
        inputStream.close();
        response.setContentType("audio/wav");
        OutputStream out = response.getOutputStream();
        out.write(buff);
        out.close();
    }
}
