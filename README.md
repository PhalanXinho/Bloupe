    mvn clean package
    mvn install -pl shared-module
    mvn docker:build docker:push -pl crawler,indexer,query