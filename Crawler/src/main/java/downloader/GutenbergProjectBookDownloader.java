package downloader;

import domain.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.Scanner;

public class GutenbergProjectBookDownloader implements BookDownloader {

    Logger logger = LoggerFactory.getLogger(GutenbergProjectBookDownloader.class);

    @Override
    public Book download(Integer id) throws BookNotFoundException {
        logger.info("Downloading book with id=" + id + "...");
        try {
            String content = downloadBook(id);
            return new Book(id, content);
        } catch (IOException e) {
            throw new BookNotFoundException("Book with id=" + id + " not found in the Gutenberg project library");
        }
    }

    private URL createURLFromId(int id) {
        try {
            return new URI("https://www.gutenberg.org/cache/epub/" + id + "/pg" + id + ".txt").toURL();
        } catch (URISyntaxException | MalformedURLException e) {
            //Should never throw
            throw new RuntimeException(e);
        }
    }

    public String downloadBook(int id) throws IOException {
        URLConnection connection;
        InputStream response;
        URL url = createURLFromId(id);

        connection = url.openConnection();
        response = connection.getInputStream();

        Scanner scanner = new Scanner(response);
        return scanner.useDelimiter("\\A").next();
    }
}
