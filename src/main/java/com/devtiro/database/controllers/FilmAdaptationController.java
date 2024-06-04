package com.devtiro.database.controllers;

import com.devtiro.database.domain.AuthorEntity;
import com.devtiro.database.domain.BookEntity;
import com.devtiro.database.domain.FilmAdaptationEntity;
import com.devtiro.database.domain.dto.AuthorDto;
import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.dto.FilmAdaptationDto;
import com.devtiro.database.mappers.Mapper;
import com.devtiro.database.services.FilmAdaptationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class FilmAdaptationController {

    private Mapper<FilmAdaptationEntity, FilmAdaptationDto> filmMapper;
    private FilmAdaptationService filmService;

    public FilmAdaptationController(Mapper<FilmAdaptationEntity, FilmAdaptationDto> filmMapper, FilmAdaptationService filmService) {
        this.filmMapper = filmMapper;
        this.filmService = filmService;
    }

    @PutMapping("/films/{title}")
    public ResponseEntity<FilmAdaptationDto> createUpdateFilm(@PathVariable("title") String title, @RequestBody FilmAdaptationDto filmDto) {
        FilmAdaptationEntity filmEntity = filmMapper.mapFrom(filmDto);
        boolean filmExists = filmService.isExists(title);
        FilmAdaptationEntity savedFilmEntity = filmService.createUpdateFilm(title, filmEntity);
        FilmAdaptationDto savedUpdateFilmDto = filmMapper.mapTo(savedFilmEntity);

        if (filmExists) {
            return new ResponseEntity(savedUpdateFilmDto, HttpStatus.OK);
        } else {
            return new ResponseEntity(savedUpdateFilmDto, HttpStatus.CREATED);
        }
    }

    @DeleteMapping(path = "/films/{title}")
    public ResponseEntity deleteFilm(@PathVariable("title") String title) {
        filmService.delete(title);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @GetMapping(path = "/films")
    public List<FilmAdaptationDto> listFilms() {
        List<FilmAdaptationEntity> authors = filmService.findAll();
        return authors.stream()
                .map(filmMapper::mapTo)
                .collect(Collectors.toList());
    }


    @GetMapping(path = "/films/{title}")
    public ResponseEntity<FilmAdaptationDto> getFilm(@PathVariable("title") String title) {
        Optional<FilmAdaptationEntity> foundFilm = filmService.findOne(title);
        return foundFilm.map(filmEntity -> {
            FilmAdaptationDto filmDto = filmMapper.mapTo(filmEntity);
            return new ResponseEntity<>(filmDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}
