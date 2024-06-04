package com.devtiro.database.controllers;

import com.devtiro.database.TestDataUtil;
import com.devtiro.database.domain.BookEntity;
import com.devtiro.database.domain.FilmAdaptationEntity;
import com.devtiro.database.domain.dto.BookDto;
import com.devtiro.database.domain.dto.FilmAdaptationDto;
import com.devtiro.database.services.FilmAdaptationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class FilmControllerIntegrationTest {
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    FilmAdaptationService filmService;

    @Autowired
    public FilmControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper, FilmAdaptationService filmService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.filmService = filmService;
    }

    @Test
    public void testThatCreateFilmReturnsHttpStatus201Created() throws Exception {
        FilmAdaptationDto filmDto = TestDataUtil.createTestFilmA();
        String createFilmJson = objectMapper.writeValueAsString(filmDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/films/" + filmDto.getTitle())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createFilmJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateFilmReturnsCreatedFilm() throws Exception {
        FilmAdaptationDto filmDto = TestDataUtil.createTestFilmA();
        String createFilmJson = objectMapper.writeValueAsString(filmDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/films/" + filmDto.getTitle())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createFilmJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(filmDto.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.director").value(filmDto.getDirector())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.yearOfProduction").value(filmDto.getYearOfProduction())
        );
    }

    @Test
    public void testThatGetFilmReturnsHttpStatus404WhenFilmDoesntExists() throws Exception {
        FilmAdaptationEntity filmEntity = TestDataUtil.createTestFilmB(null);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + filmEntity.getTitle())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatListFilmReturnsHttpStatus200Ok() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/films")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListFilmsReturnsFilms() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookA(null);
        FilmAdaptationEntity filmEntity = TestDataUtil.createTestFilmB(testBookEntityA);
        filmService.createUpdateFilm(filmEntity.getTitle(), filmEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/films")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value("The Shadow in the Attic")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].director").value("director")
        );
    }

    @Test
    public void testThatGetFilmReturnsHttpStatus200OkWhenFilmExists() throws Exception {
        FilmAdaptationEntity filmEntity = TestDataUtil.createTestFilmB(null);
        filmService.createUpdateFilm(filmEntity.getTitle(), filmEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/films/" + filmEntity.getTitle())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateFilmReturnsHttpStatus200Ok() throws Exception {
        FilmAdaptationEntity filmEntity = TestDataUtil.createTestFilmB(null);
        FilmAdaptationEntity savedFilmEntity = filmService.createUpdateFilm(
                filmEntity.getTitle(), filmEntity
        );

        FilmAdaptationDto testFilm = TestDataUtil.createTestFilmA();
        testFilm.setTitle(savedFilmEntity.getTitle());
        String filmJson = objectMapper.writeValueAsString(testFilm);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/films/The Shadow in the Attic")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatDeleteExistingFilmReturnsHttpStatus204NoContent() throws Exception {
        FilmAdaptationEntity filmEntity = TestDataUtil.createTestFilmB(null);
        filmService.createUpdateFilm(filmEntity.getTitle(), filmEntity);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/films/" + filmEntity.getTitle())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteNoNExistingFilmReturnsHttpStatus204NoContent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/films/umpalumpa")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
