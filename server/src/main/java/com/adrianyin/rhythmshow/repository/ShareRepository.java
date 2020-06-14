package com.adrianyin.rhythmshow.repository;

import com.adrianyin.rhythmshow.domain.Share;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface ShareRepository extends PagingAndSortingRepository<Share, Integer> {

    Page<Share> findByTypeInAndIsOriginalInAndIsPublished(Collection<Integer> types,
                                            Collection<Integer> isOriginals,
                                            int isPublished,
                                            Pageable pageable);

    Share getById(int id);
}
