package datalake.downloader;

import java.io.IOException;

public interface Downloader {

    void download() throws IOException;
}
