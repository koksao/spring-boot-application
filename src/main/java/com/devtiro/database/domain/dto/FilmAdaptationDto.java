package com.devtiro.database.domain.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilmAdaptationDto {
    @Id
    private String title;

    private Integer yearOfProduction;

    private String director;

    private BookDto book;
}
