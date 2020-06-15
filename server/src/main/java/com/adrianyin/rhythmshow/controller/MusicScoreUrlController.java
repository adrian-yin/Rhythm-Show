package com.adrianyin.rhythmshow.controller;

import com.adrianyin.rhythmshow.config.IgnoreSecurity;
import com.adrianyin.rhythmshow.result.Result;
import com.adrianyin.rhythmshow.result.ResultFactory;
import com.adrianyin.rhythmshow.service.MusicScorePicService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MusicScoreUrlController {

    final MusicScorePicService musicScorePicService;

    public MusicScoreUrlController(MusicScorePicService musicScorePicService) {
        this.musicScorePicService = musicScorePicService;
    }

    @IgnoreSecurity
    @PostMapping(value = "/api/getscoreurl")
    @ResponseBody
    public Result getScoreUrl(@RequestBody Map<String, String> requestBody) {
        String name = requestBody.get("name");
        String url = musicScorePicService.getUrlByName(name);
        if (!url.equals("")) {
            Map<String, String> data = new HashMap<String, String>();
            data.put("url", url);
            return ResultFactory.buildSuccessResult(data);
        } else {
            return ResultFactory.buildFailResult("抱歉，没有你想找的乐谱。");
        }
    }
}
