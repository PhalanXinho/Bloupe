    mvn clean package
    mvn docker:build docker:push -pl crawler,indexer,query