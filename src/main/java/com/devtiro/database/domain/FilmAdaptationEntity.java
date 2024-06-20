package com.devtiro.database.domain;

import com.devtiro.database.domain.dto.BookDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "films")
public class FilmAdaptationEntity {
    @Id
    private String title;

    private Integer yearOfProduction;

    private String director;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "book_isbn")
    private BookEntity bookEntity;
}
