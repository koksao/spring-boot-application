package com.devtiro.database.domain.dto;


import com.devtiro.database.domain.AuthorEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    @Id
    private String isbn;

    private String title;

    private AuthorDto author;

}

