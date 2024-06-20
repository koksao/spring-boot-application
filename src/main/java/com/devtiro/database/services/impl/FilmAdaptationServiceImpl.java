package com.devtiro.database.services.impl;

import com.devtiro.database.domain.FilmAdaptationEntity;
import com.devtiro.database.repositories.FilmAdaptationRepository;
import com.devtiro.database.services.FilmAdaptationService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class FilmAdaptationServiceImpl implements FilmAdaptationService {

    private FilmAdaptationRepository filmRepository;

    public FilmAdaptationServiceImpl(FilmAdaptationRepository filmRepository){
        this.filmRepository = filmRepository;
    }
    @Override
    public FilmAdaptationEntity createUpdateFilm(String title, FilmAdaptationEntity film) {
        film.setTitle(title);
        return filmRepository.save(film);
    }

    @Override
    public List<FilmAdaptationEntity> findAll() {
        return StreamSupport
                .stream(
                        filmRepository.findAll().spliterator(),
                        false)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<FilmAdaptationEntity> findOne(String title) {
        return filmRepository.findById(title);
    }

    @Override
    public void delete(String title) {
        filmRepository.deleteById(title);
    }

    @Override
    public boolean isExists(String title) {
        return filmRepository.existsById(title);
    }

}
