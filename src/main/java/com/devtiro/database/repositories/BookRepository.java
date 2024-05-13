package com.devtiro.database.repositories;

import com.devtiro.database.domain.BookEntity;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<BookEntity,String> {
}
