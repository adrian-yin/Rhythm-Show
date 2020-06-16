package com.adrianyin.rhythmshow.repository;

import com.adrianyin.rhythmshow.domain.Share;
import com.adrianyin.rhythmshow.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;
import java.util.List;

public interface ShareRepository extends PagingAndSortingRepository<Share, Integer> {

    Page<Share> findByTypeInAndIsOriginalInAndIsPublished(Collection<Integer> types,
                                            Collection<Integer> isOriginals,
                                            int isPublished,
                                            Pageable pageable);

    Share getById(int id);
    List<Share> getByUser(User user);
}
