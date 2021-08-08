package com.ritesh.springgraphql.controller;

import com.ritesh.springgraphql.service.GraphQLService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    GraphQLService graphQLService;

    @PostMapping
    public Object getAllBooks(@RequestBody String query) {
        ExecutionResult result = graphQLService.getGraphQL().execute(query);
        return result.getData();
    }
}
