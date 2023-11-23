import java.io.IOException;


public class Main {
    private static String bookPath;

    public static void main(String[] args) throws InterruptedException {
        try {
            new Controller().execute("C:\\Users\\irene\\Desktop\\pg71894.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
