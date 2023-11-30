package datalake;

public interface DataLakeManager {

    String read(String fileName);

    int getIdFromFileName(String fileName);

}
