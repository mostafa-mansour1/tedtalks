/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.io.tedtalks.entity;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.io.tedtalks.deserialize.TalksDeserializer;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 *
 * @author mostafa
 */
@Entity
@Table(name = "talks")
@JsonDeserialize(using = TalksDeserializer.class)
public class Talks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", nullable = false, length = 150)
    private String title;
    
    @Column(name = "author", nullable = false, length = 100)
    private String author;
    
    @Column(name = "InDate")
    private Date date;
    
    @Column(name = "views")
    private Long views;
    
    @Column(name = "likes")
    private Long likes;
    
    @Column(name = "link", length = 200)
    private String link;
    
    private Integer statusCode; 

    public Talks() {
        super();
    }

    public Integer getStatusCode() {
        return 0;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getViews() {
        return views;
    }

    public void setViews(Long views) {
        if(views < 0) views =Long.valueOf("0");
        this.views = views;
    }
    
    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        if(likes < 0) likes =Long.valueOf("0");
        this.likes = likes;
    }
    
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Talks(
            String title, 
            String author,
            Date date,
          Long views,
          Long likes,
          String link
          ) {
        super();
        this.title = title;
        this.author = author;
        this.date = date;
        this.views = views;
        this.likes = likes;
        this.link = link;
    }
}
