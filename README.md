# Search Engine Project

This project aims to develop a search engine comprising a Crawler, Indexer, and Query modules. It was initiated as part of the Big Data course taught at the University of Las Palmas de Gran Canaria (ULPGC).

## Building Docker Images

To build all the Docker images, execute the following commands:

    mvn clean package
    mvn install -pl shared-module
    mvn docker:build docker:push -pl crawler,indexer,query


## Team members
The project was created and contributed to by:
- Yang, Jia Hao
- León Quintana, Gerardo
- Nagy, Jakub 
- Guerra Déniz, Irene
- García Nuez, Raúl
- Rivero Sánchez, Raúl
