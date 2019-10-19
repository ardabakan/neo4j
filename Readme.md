#Neo4j Spring Boot Best Practice

This project makes use of a popular Graph DB implementation called Neo4J

![](https://neo4j.com/wp-content/themes/neo4jweb/assets/images/neo4j-logo-2015.png)

- Since the input data of IMDB is SO immense, i had to limit the input to 10000 lines for each TSV file (You can change from *application.properties* file)
- Please keep the downloaded imdb files in such a structure :

`D:\imdb\name.basics.tsv\data.tsv`
`D:\imdb\title.ratings.tsv\data.tsv`  

etc..

The `D:\imdb\` part is also configurable from *application.properties* file


https://neo4j.com/developer/guide-cypher-basics/#_solve_the_six_degrees_question


####Installation and Running
- To build the project, unzip the file and navigate to the root of the project
- Make sure that  *Maven* is installed

#####Building

`$ mvn clean install`

#####Running

`$ mvn spring-boot:run`

#####Try the endpoints

`$ http://localhost:8080/api/reload` for loading Movie, Actor, Roles and Ratings

`$ http://localhost:8080/movies/topMovies/horror` for top rated movies with "horror" genre

![6 degrees of Kevin Bacon](https://maxdemarzidotcom.files.wordpress.com/2012/01/kevin-bacon-six-degrees-590x350.jpg "6 degrees of Kevin Bacon")

Thanks for reading so far!
