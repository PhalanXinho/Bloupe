import datalakeuploader.DataLakeBookUploader;
import datalakeuploader.GoogleCloudDataLakeBookUploader;
import domain.Book;

public class Controller {

    DataLakeBookUploader dataLakeBookUploader = new GoogleCloudDataLakeBookUploader();

    public void run() {
        Book test = new Book(4132, "TEST");
        dataLakeBookUploader.upload( test );
    }
}
