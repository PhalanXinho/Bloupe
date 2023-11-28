import broker.ArtemisMQBooksConsumer;

public class Main {
    private static String bookPath;

    public static void main(String[] args) {

        ArtemisMQBooksConsumer artemisMQFilesConsumer = new ArtemisMQBooksConsumer();
        System.out.println( artemisMQFilesConsumer.consume() );

        /*
        try {
            new Controller().execute("C:\\Users\\irene\\Desktop\\pg71894.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


         */
    }

}
