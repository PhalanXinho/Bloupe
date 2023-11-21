package datalake;

import java.nio.file.Path;

public interface Datalake {
    void build();

    Path getPath();
}
