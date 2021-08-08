package com.ritesh.springgraphql.service;

import com.ritesh.springgraphql.model.Book;
import com.ritesh.springgraphql.repository.BookRepository;
import com.ritesh.springgraphql.service.datafetcher.AllBooksDataFetcher;
import com.ritesh.springgraphql.service.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class GraphQLService {

    @Value("classpath:books.graphql")
    Resource resource;

    GraphQL graphQL;

    @Autowired
    AllBooksDataFetcher allBooksDataFetcher;

    @Autowired
    BookDataFetcher bookDataFetcher;

    @Autowired
    BookRepository bookRepository;


    @PostConstruct
    public void loadSchema() throws IOException {
        loadDataIntoHSQL();
        var schemaFile = resource.getFile();
        var typeRegistry = new SchemaParser().parse(schemaFile);
        var wiring = buildRuntimeWiring();
        var schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private void loadDataIntoHSQL() {
        Stream.of(
                new Book("123", "XYZ", "Kindle", "Ritesh", "Aug 7"),
                new Book("1234", "ABC", "Kindle", "Pankaj", "Aug 7"),
                new Book("12345", "DEF", "Kindle", "Shivam", "Aug 7"),
                new Book("123456", "GHI", "Kindle", "Deependra", "Aug 7")
        )
                .forEach(bookRepository::save);
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allBooks", allBooksDataFetcher)
                        .dataFetcher("book", bookDataFetcher))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
