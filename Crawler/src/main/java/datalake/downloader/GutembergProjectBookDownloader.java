package datalake.downloader;

import com.hazelcast.collection.IList;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class GutembergProjectBookDownloader implements Downloader {
    private static final String bookUrl = "https://www.gutenberg.org/cache/epub/";
    private static final String page = "/pg";
    private static final String extension = ".txt";

    private static final int MAX_BOOKS = 70_000;

    private final Path path;

    public GutembergProjectBookDownloader(Path path) {
        this.path = path;
    }

    @Override
    public void download() throws IOException {
        int randInt = new RandomNumberGenerator(MAX_BOOKS).generateRandomNumber();
        String finalDirectory = getFinalDirectory(randInt);
        URL url = new URL(bookUrl + randInt + page + randInt + extension);
        File file = new File(finalDirectory);
        FileUtils.copyURLToFile(url, file);
        System.out.println("Downloading into: " + finalDirectory);
        if (new FileEncodingHandler().checkFileEncoding(finalDirectory)) {
            Config config = new Config();
            HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(config);
            IList<String> downloadedBooks = hazelcastInstance.getList("downloadedBooks");
            downloadedBooks.add(finalDirectory);
        }
    }

    private String getFinalDirectory(int randInt) {
        int lastDigit = Integer.parseInt(String.valueOf(randInt).
                substring(String.valueOf(randInt).length() - 1));
        String bookName = lastDigit + "/book" + randInt + extension;
        return String.valueOf(this.path.resolve(bookName));
    }
}
