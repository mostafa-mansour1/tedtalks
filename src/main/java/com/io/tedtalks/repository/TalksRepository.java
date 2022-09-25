/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.io.tedtalks.repository;

import com.io.tedtalks.entity.Talks;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author mostafa
 */
public interface TalksRepository extends JpaRepository<Talks, Long> {

//    @Cacheable("talks")
//    Talks findOne(String name);
    
//    @Query("select * from talks where id = 1")
    public Page<Talks> findByTitleContainingIgnoreCaseAndViewsGreaterThanEqualAndLikesGreaterThanEqual(String title,Long views,Long likes, Pageable pageable);
    
    public Page<Talks> findByAuthorContainingIgnoreCaseAndViewsGreaterThanEqualAndLikesGreaterThanEqual(String author,Long views,Long likes, Pageable pageable);
    
    public Page<Talks> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseAndViewsGreaterThanAndLikesGreaterThan(String title,String author,Long views,Long likes, Pageable pageable);
    
    public Page<Talks> findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndViewsGreaterThanEqualAndLikesGreaterThanEqual(String title,String author,Long views,Long likes, Pageable pageable);
    
    public Page<Talks> findByViewsGreaterThanEqual(Long views, Pageable pageable);
    
    public Page<Talks> findByLikesGreaterThanEqual(Long likes, Pageable pageable);
    
    public Page<Talks> findByViewsGreaterThanEqualAndLikesGreaterThanEqual(Long views,Long likes, Pageable pageable);

    @Override
    @Cacheable("talks")
    Optional<Talks> findById(Long id);

    @Override
    @Cacheable("talks")
    @CacheEvict(value = "talks", allEntries = true)
     Page<Talks> findAll(Pageable paging);

    @CachePut("talks")
    @Override
    Talks save(Talks entity);
    

    @Override
    @CacheEvict("talks")
    void deleteById(Long id);
   
}
