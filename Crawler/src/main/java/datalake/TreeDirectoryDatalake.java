package datalake;

import datalake.reader.Reader;
import datalake.structure.TreeDirectoryStructure;
import datalake.downloader.Downloader;
import datalake.downloader.GutembergProjectBookDownloader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TreeDirectoryDatalake implements Datalake, Downloader {

    private final Path path;

    public TreeDirectoryDatalake(Path path) {
        this.path = dateRegister(path);
        new TreeDirectoryStructure(this.path).createStructure();

    }

    @Override
    public void feed() {
        try {
            download();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void read() {
        new Reader().read(this.path);
    }

    private Path dateRegister(Path root) {
        Path datePath = new DatePathProvider().provideDatePath();
        Path path = root.resolve(datePath);
        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    @Override
    public void download() throws IOException {
        new GutembergProjectBookDownloader(this.path).download();
    }
}
