package com.example.demo.controller;

import com.example.demo.model.Movie;
import com.example.demo.model.Movie.WatchStatus;
import com.example.demo.model.User;
import com.example.demo.service.MovieService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://moviediary-frontend.vercel.app")
@RestController
@RequestMapping("/movie")
public class MovieController {
    private final MovieService service;

    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public List<Movie> getMovies(@AuthenticationPrincipal User user) {
        return service.getAllMovies(user.getId());
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/create")
    public Movie createMovie(@RequestBody Movie movie, @AuthenticationPrincipal User user) {
        return service.createMovie(movie, user);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody Movie movie,
                             @AuthenticationPrincipal User user) {
        return service.updateMovie(id, movie, user.getId());
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id, @AuthenticationPrincipal User user) {
        service.deleteMovie(id, user.getId());
    }

    @GetMapping("/status/{status}")
    public List<Movie> getByStatus(@PathVariable WatchStatus status,
                                   @AuthenticationPrincipal User user) {
        return service.getByStatus(status, user.getId());
    }

    @GetMapping("/search")
    public List<Movie> getByTitle(@RequestParam String title,
                                  @AuthenticationPrincipal User user) {
        return service.getByTitle(title, user.getId());
    }
}
