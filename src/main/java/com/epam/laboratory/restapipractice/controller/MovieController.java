package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.entity.MovieEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
public class MovieController {

    @PostMapping(value = "/movie", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<String> validateBody(@Valid @RequestBody MovieEntity movieEntity) {
        return ResponseEntity.ok("Movie is valid");
    }
}
