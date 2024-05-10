package com.devtiro.database.repositories;

import com.devtiro.database.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book,String> {
}
