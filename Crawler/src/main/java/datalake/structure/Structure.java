package datalake.structure;

import java.nio.file.Path;

public interface Structure {
    void createStructure();

    Path getStructurePath();
}
