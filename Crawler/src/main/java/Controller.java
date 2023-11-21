import datalake.Datalake;
import datalake.TreeDirectoryDatalake;
import downloader.Downloader;
import downloader.GutembergProjectBookDownloader;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Controller implements Datalake, Downloader {
    private final Path path = Paths.get("Crawler/datalake");
    private static final int MAX_ITERS = 6;

    public void execute() throws IOException {
        build();
        for (int i = 1; i < MAX_ITERS; i++) {
            download();
        }

    }

    @Override
    public void build() {
        new TreeDirectoryDatalake(path).build();
    }

    @Override
    public Path getPath() {
        return new TreeDirectoryDatalake(path).getPath();
    }

    @Override
    public void download() throws IOException {
        new GutembergProjectBookDownloader(getPath()).download();
    }
}
