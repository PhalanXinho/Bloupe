import datalake.Datalake;
import datalake.TreeDirectoryDatalake;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Controller implements Datalake {
    private final Path path = Paths.get("Crawler/datalake");
    private static final int MAX_ITERS = 6;
    private final TreeDirectoryDatalake treeDirectoryDatalake = new TreeDirectoryDatalake(path);


    public void execute() throws IOException {
        for (int i = 1; i < MAX_ITERS; i++) {
            feed();
        }
        read();
    }

    @Override
    public void feed() {
        treeDirectoryDatalake.feed();
    }

    @Override
    public void read() {
        treeDirectoryDatalake.read();
    }

}
