import broker.ArtemisMQBooksConsumer;
import broker.BooksConsumer;
import datalake.DataLakeManager;
import datalake.GoogleCloudDataLakeManager;

public class Main {
    private static String bookPath;

    public static void main(String[] args) {

        BooksConsumer artemisMQFilesConsumer = new ArtemisMQBooksConsumer();
        DataLakeManager dataLakeManager = new GoogleCloudDataLakeManager();

        while (true) {
            String fileName = artemisMQFilesConsumer.consume();
            String content = dataLakeManager.read( fileName );
            System.out.println( content );
        }

        /*
        try {
            new Controller().execute("C:\\Users\\irene\\Desktop\\pg71894.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


         */
    }

}
