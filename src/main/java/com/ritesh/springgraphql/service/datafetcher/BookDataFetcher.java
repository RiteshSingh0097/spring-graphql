package com.ritesh.springgraphql.service.datafetcher;

import com.ritesh.springgraphql.model.Book;
import com.ritesh.springgraphql.repository.BookRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookDataFetcher implements DataFetcher<Book> {

    @Autowired
    BookRepository bookRepository;

    @Override
    public Book get(DataFetchingEnvironment environment) {
        return bookRepository.findById(environment.getArgument("id")).orElse(null);
    }
}
