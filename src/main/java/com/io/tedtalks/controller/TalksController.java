/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.io.tedtalks.controller;

import com.io.tedtalks.entity.Talks;
import com.io.tedtalks.exception.InternalServerError;
import com.io.tedtalks.exception.TalksNotFound;
import com.io.tedtalks.repository.TalksRepository;
import com.io.tedtalks.response.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author mostafa
 */
@RestController
@RequestMapping("/api/v1/talks")
public class TalksController {

    @Autowired
    TalksRepository talksRepository;

    // Create one talk
    @PostMapping
    public ResponseEntity<Talks>
            createTalks(@RequestBody Talks talk) {
        try {

            Talks newTalk = new Talks(
                    talk.getTitle(),
                    talk.getAuthor(),
                    talk.getDate(),
                    talk.getViews(),
                    talk.getLikes(),
                    talk.getLink()
            );
            talksRepository.save(newTalk);
            return new ResponseEntity<>(newTalk, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new InternalServerError(e.getLocalizedMessage());
        }
    }

    @PostMapping("/bulk")
    public ResponseEntity<Iterable<Talks>>
            createTalks(@RequestBody ArrayList<Talks> talks) {
        try {
            List<Talks> talksList = new ArrayList<>();
            if (talks.size() <= 1) {
                throw new InternalServerError("must add one record at lease");

            }
            for (int i = 0; i < talks.size(); i++) {
                Talks talk = talks.get(i);
                Talks newTalk = new Talks(
                        talk.getTitle(),
                        talk.getAuthor(),
                        talk.getDate(),
                        talk.getViews(),
                        talk.getLikes(),
                        talk.getLink()
                );
                talksList.add(newTalk);

            }
            return new ResponseEntity<>(talksRepository.saveAll(talksList), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new InternalServerError(e.getLocalizedMessage());
        }
    }

    // Update talks
    @PutMapping("/{id}")
    public ResponseEntity<Talks>
            updateTalks(@PathVariable("id") Long id,
                    @RequestBody Talks talk) {

        Optional<Talks> talkData = talksRepository.findById(id);
        if (talkData.isPresent()) {
            Talks oneTalk = talkData.get();
            oneTalk.setTitle(talk.getTitle());
            oneTalk.setAuthor(talk.getAuthor());

            oneTalk.setDate(talk.getDate());
            
            oneTalk.setViews(talk.getViews());
            oneTalk.setLikes(talk.getLikes());

            oneTalk.setLink(talk.getLink());
            return new ResponseEntity<>(talksRepository.save(oneTalk), HttpStatus.CREATED);

        } else {
            throw new TalksNotFound("Invalid Talk Id");
        }
    }

    // increase one like
    @PatchMapping("/increaseLikes/{id}")
    public ResponseEntity<Talks>
            increasLikesTalks(@PathVariable("id") Long id) {
        Optional<Talks> talkData = talksRepository.findById(id);
        if (talkData.isPresent()) {

            Talks oneTalk = talkData.get();
            oneTalk.setLikes(oneTalk.getLikes() + 1);

            return new ResponseEntity(talksRepository.save(oneTalk), HttpStatus.CREATED);
        } else {

            throw new TalksNotFound("Invalid Talk Id");
        }
    }

    // increase one view
    @PatchMapping("/increaseViews/{id}")
    public ResponseEntity<Talks>
            increasViewsTalks(@PathVariable("id") Long id) {

        Optional<Talks> talkData = talksRepository.findById(id);
        if (talkData.isPresent()) {

            Talks oneTalk = talkData.get();
            oneTalk.setViews(oneTalk.getViews() + 1);

            return new ResponseEntity<>(talksRepository.save(oneTalk), HttpStatus.CREATED);
        } else {
            throw new TalksNotFound("Invalid Talk Id");
        }
    }

    // Get all Talks
    @GetMapping
    public ResponseEntity<List<Talks>> getAllTalkss(
            @RequestParam(defaultValue = "1") Integer pageNo,
            @RequestParam(defaultValue = "30") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortType,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "0") Long views,
            @RequestParam(defaultValue = "0") Long likes) {
        try {
            List<Talks> talks = new ArrayList<Talks>();
            if (pageNo < 1) {
                pageNo = 1;
            }
            if (pageSize < 10) {
                pageSize = 10;
            }

            Sort sort;
            if (sortType.equalsIgnoreCase("desc")) {
                sort = Sort.by(sortBy).descending();
            } else {
                sort = Sort.by(sortBy).ascending();

            }

            Pageable paging = PageRequest.of(pageNo - 1, pageSize, sort);
            if (title != null || author != null) {

                if (title != null && author == null) {
                    talksRepository.findByTitleContainingIgnoreCaseAndViewsGreaterThanEqualAndLikesGreaterThanEqual(title, views, likes, paging).forEach(talks::add);
                } else if (title == null && author != null) {
                    talksRepository.findByAuthorContainingIgnoreCaseAndViewsGreaterThanEqualAndLikesGreaterThanEqual(author, views, likes, paging).forEach(talks::add);
                } else {
                    talksRepository.findByTitleContainingIgnoreCaseAndAuthorContainingIgnoreCaseAndViewsGreaterThanEqualAndLikesGreaterThanEqual(title, author, views, likes, paging).forEach(talks::add);
                }

            } else {
                talksRepository.findByViewsGreaterThanEqualAndLikesGreaterThanEqual(views, likes, paging).forEach(talks::add);
            }
            return new ResponseEntity<>(talks, HttpStatus.OK);
        } catch (Exception e) {
            throw new InternalServerError(e.getMessage());
        }

    }

    // Get talks by ID
    @GetMapping("/{id}")
    public ResponseEntity<Talks>
            getTalksByID(@PathVariable("id") Long id) {
        Optional<Talks> talkData = talksRepository.findById(id);
        if (talkData.isPresent()) {

            return new ResponseEntity<>(talkData.get(), HttpStatus.OK);

        } else {
            throw new TalksNotFound("Invalid Talks Id");
        }

    }

    // Delete talks
    @DeleteMapping("/{id}")
    public ResponseEntity<Object>
            deleteTalks(@PathVariable("id") Long id) {
        Optional<Talks> talkData = talksRepository.findById(id);
        if (talkData.isPresent()) {
            talksRepository.deleteById(id);

            ApiResponse apiResponse = new ApiResponse("Entry deleted succesfully", HttpStatus.OK);
            return new ResponseEntity<>(apiResponse, HttpStatus.OK);
//            return success.message("Entry deleted succesfully");
        } else {
            throw new TalksNotFound("Invalid Talks Id");
        }
    }
}
