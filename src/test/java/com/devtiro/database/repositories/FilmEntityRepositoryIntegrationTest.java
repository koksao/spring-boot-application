package com.devtiro.database.repositories;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.AuthorEntity;
import com.devtiro.database.domain.BookEntity;
import com.devtiro.database.domain.FilmAdaptationEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class FilmEntityRepositoryIntegrationTest {

    FilmAdaptationRepository filmRepository;

    @Autowired
    FilmEntityRepositoryIntegrationTest(FilmAdaptationRepository filmRepository){
        this.filmRepository = filmRepository;
    }

    @Test
    public void testThatFilmCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        FilmAdaptationEntity filmEntity = TestDataUtil.createTestFilmB(bookEntity);
        filmRepository.save(filmEntity);
        Optional<FilmAdaptationEntity> result = filmRepository.findById(filmEntity.getTitle());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(filmEntity);
    }

    @Test
    public void testThatFilmCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorEntity);
        FilmAdaptationEntity filmEntity = TestDataUtil.createTestFilmB(bookEntityA);
        filmRepository.save(filmEntity);

        filmEntity.setDirector("UPDATED");
        filmRepository.save(filmEntity);

        Optional<FilmAdaptationEntity> result = filmRepository.findById(filmEntity.getTitle());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(filmEntity);
    }

    @Test
    public void testThatBookCanBeDeleted(){
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorEntity);
        FilmAdaptationEntity filmEntity = TestDataUtil.createTestFilmB(bookEntityA);

        filmRepository.save(filmEntity);

        filmRepository.deleteById(filmEntity.getTitle());
        Optional<FilmAdaptationEntity> result = filmRepository.findById(filmEntity.getTitle());
        assertThat(result).isEmpty();

    }
}
