package com.adrianyin.rhythmshow.repository;

import com.adrianyin.rhythmshow.domain.MusicScorePic;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MusicScorePicRepository extends CrudRepository<MusicScorePic, Integer> {
    List<MusicScorePic> getByNameLike(String name);
}
