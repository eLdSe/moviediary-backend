package com.example.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Table(name = "movie")
public class Movie {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    
    public enum WatchStatus{
        WANT,
        WATCHING,
        WATCHED
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Long id;
    
    private String title;
    private String director;
    @Column(name = "release_year")
    private Integer year;
    private Float rating;
    private String poster;

    @Enumerated(EnumType.STRING)
    private WatchStatus status;
    


    // users
    public User getUser(){return user;}
    public void setUser(User user){this.user = user;}

    // id 
    public Long getId(){return id;}
    public void setId(Long id){this.id = id;}

    // title
    public String getTitle(){return title;}
    public void setTitle(String title){this.title = title;}

    // director
    public String getDirector(){return director;}
    public void setDirector(String director){this.director = director;}

    // year
    public Integer getYear(){return year;}
    public void setYear(Integer year){this.year = year;}
    
    // rating
    public Float getRating(){return rating;}
    public void setRating(Float rating){this.rating = rating;}

    // status
    public WatchStatus getStatus(){return status;}
    public void setStatus(WatchStatus status){this.status = status;}

    // poster
    public String getPoster(){return poster;}
    public void setPoster(String poster) {this.poster = poster;}

}
