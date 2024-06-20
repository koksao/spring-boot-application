package com.devtiro.database.repositories;

import com.devtiro.database.domain.FilmAdaptationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmAdaptationRepository extends CrudRepository<FilmAdaptationEntity, String> {
}
