package com.example.demo.service;

import com.example.demo.model.Movie;
import com.example.demo.model.Movie.WatchStatus;
import com.example.demo.model.User;
import com.example.demo.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository repository;

    public MovieService(MovieRepository repository) {
        this.repository = repository;
    }

    public List<Movie> getAllMovies(Long userId) {
        return repository.findByUserId(userId);
    }

    public Movie getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
    }

    public Movie createMovie(Movie movie, User user) {
        movie.setUser(user);
        return repository.save(movie);
    }

    public Movie updateMovie(Long id, Movie updateMovie, Long userId) {
        Movie movie = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found"));

        if (!movie.getUser().getId().equals(userId)) {
            throw new RuntimeException("Нет доступа");
        }

        movie.setDirector(updateMovie.getDirector() != null ? updateMovie.getDirector() : movie.getDirector());
        movie.setRating(updateMovie.getRating()     != null ? updateMovie.getRating()     : movie.getRating());
        movie.setStatus(updateMovie.getStatus()     != null ? updateMovie.getStatus()     : movie.getStatus());
        movie.setTitle(updateMovie.getTitle()       != null ? updateMovie.getTitle()       : movie.getTitle());
        movie.setYear(updateMovie.getYear()         != null ? updateMovie.getYear()         : movie.getYear());
        movie.setPoster(updateMovie.getPoster()     != null ? updateMovie.getPoster()     : movie.getPoster());

        return repository.save(movie);
    }

    public void deleteMovie(Long id, Long userId) {
        Movie movie = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Movie not found"));

        if (!movie.getUser().getId().equals(userId)) {
            throw new RuntimeException("Нет доступа");
        }

        repository.deleteById(id);
    }

    public List<Movie> getByStatus(WatchStatus status, Long userId) {
        return repository.findByUserIdAndStatus(userId, status);
    }

    public List<Movie> getByTitle(String title, Long userId) {
        return repository.findByUserIdAndTitleContainingIgnoreCase(userId, title);
    }
}