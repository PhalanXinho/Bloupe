import java.io.IOException;


public class Main {
    private static String bookPath;

    public static void main(String[] args) throws InterruptedException {
        try {
            new Controller().execute("Indexer\\src\\main\\resources\\pg71894.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
