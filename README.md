# spring-graphql

curl --location --request POST 'http://localhost:8080/books' \
--header 'Content-Type: text/plain' \
--data-raw '{
    allBooks {
        authors
        publishedDate
    }
}'


curl --location --request POST 'http://localhost:8080/books' \
--header 'Content-Type: text/plain' \
--data-raw '{
    book(id: "123") {
        authors
        publishedDate
    }
}'
