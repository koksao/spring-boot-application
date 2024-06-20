package com.devtiro.database.services;

import com.devtiro.database.domain.FilmAdaptationEntity;

import java.util.List;
import java.util.Optional;

public interface FilmAdaptationService {
    FilmAdaptationEntity createUpdateFilm(String title, FilmAdaptationEntity film);

    List<FilmAdaptationEntity> findAll();

    Optional<FilmAdaptationEntity> findOne(String title);

    void delete(String title);

    boolean isExists(String isbn);
}
