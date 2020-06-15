package com.adrianyin.rhythmshow.service;

import com.adrianyin.rhythmshow.domain.MusicScorePic;
import com.adrianyin.rhythmshow.repository.MusicScorePicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MusicScorePicService {

    final MusicScorePicRepository musicScorePicRepository;

    @Autowired
    public MusicScorePicService(MusicScorePicRepository musicScorePicRepository) {
        this.musicScorePicRepository = musicScorePicRepository;
    }

    // 模糊查找符合条件的第一条记录的Url
    public String getUrlByName(String name) {
        String url = "";
        List<MusicScorePic> musicScorePics = musicScorePicRepository.getByNameLike("%" + name + "%");
        if (!musicScorePics.isEmpty()) {
            url = musicScorePics.get(0).getUrl();
        }
        return url;
    }
}
