package com.example.demo.repository;

import com.example.demo.model.Movie;
import com.example.demo.model.Movie.WatchStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByUserId(Long userId);
    List<Movie> findByUserIdAndStatus(Long userId, WatchStatus status);
    List<Movie> findByUserIdAndTitleContainingIgnoreCase(Long userId, String title);
}