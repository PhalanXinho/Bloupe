package datamart;

public interface DataMartManager {
    void addWordToDataMart(IndexedWordResult indexedWordResult);

    boolean saveIntoFile(String path);

}
