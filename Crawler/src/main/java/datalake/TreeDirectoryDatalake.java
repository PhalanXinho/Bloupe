package datalake;

import datalake.structure.Structure;
import datalake.structure.TreeDirectoryStructure;

import java.nio.file.Files;
import java.nio.file.Path;

public class TreeDirectoryDatalake implements Datalake, Structure {

    private final Path path;

    public TreeDirectoryDatalake(Path path) {
        this.path = dateRegister(path);
    }

    @Override
    public void build() {
        createStructure();
    }

    private Path dateRegister(Path root){
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
    public void createStructure() {
        TreeDirectoryStructure treeDirectoryStructure = new TreeDirectoryStructure(this.path);
        treeDirectoryStructure.createStructure();
    }

    @Override
    public Path getStructurePath() {
        return new TreeDirectoryStructure(this.path).getStructurePath();
    }

    @Override
    public Path getPath() {
        return getStructurePath();
    }
}
