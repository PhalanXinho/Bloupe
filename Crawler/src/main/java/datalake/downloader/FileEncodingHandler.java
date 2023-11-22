package datalake.downloader;

import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileEncodingHandler {
    public void checkFileEncoding(String path) throws IOException {
        File file = new File(path);
        DefaultDetector detector = new DefaultDetector();
        try (InputStream input = TikaInputStream.get(file)) {
            Metadata metadata = new Metadata();
            String encoding = detector.detect(input, metadata).toString();
            if (!encoding.equals("text/plain")){
                file.delete();
                System.out.println("Deleting file: " + path + ", due to incompatible encoding");
            }
        }
    }
}