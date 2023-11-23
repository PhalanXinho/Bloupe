package datalake.structure;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidParameterException;


public class TreeDirectoryStructure {

    private static final Logger logger = LogManager.getLogger(TreeDirectoryStructure.class);
    private final Integer dirCapacity = 100;
    private final Integer capacity = 1000;
    private final Path root;

    public TreeDirectoryStructure(Path path) {
        this.root = path;
    }

    boolean isStructureCreated(Path root) {
        return root.toFile().listFiles().length != 0;
    }

    void buildTreeDirectory(Path root) throws IOException {
        Integer dirSizeDigits = (int) Math.log10(this.dirCapacity);
        Integer capacityDigits = (int) Math.log10(this.capacity);
        buildTreeDirectoryHelper(root, 0, capacityDigits - dirSizeDigits);
    }

    void buildTreeDirectoryHelper(Path location, int currentDepth, int maxDepth) throws IOException {
        if (currentDepth == maxDepth) return;

        for (int i = 0; i < 10; i++) {
            Path path = location.resolve(i + "");

            try {
                Files.createDirectory(path);
            } catch (FileAlreadyExistsException ex) {
                continue;
            }

            buildTreeDirectoryHelper(path, currentDepth + 1, maxDepth);
        }
    }

    public void createStructure() {

        logger.log(Level.INFO, "Creating Tree-Directory");

        if ((Math.log10(capacity) % 1) != 0) throw new InvalidParameterException("Capacity must be a power of 10");
        if ((Math.log10(dirCapacity) % 1) != 0)
            throw new InvalidParameterException("The capacity must be a power of 10");

        try {
            logger.log(Level.INFO, "Trying to create root directory for the datalake");
            Files.createDirectories(this.root);
            logger.log(Level.INFO, "Root directory created");
        } catch (FileAlreadyExistsException ex) {
            logger.log(Level.INFO, "Root directory already created");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!isStructureCreated(this.root)) {
            logger.log(Level.INFO, "Creating Tree-Directory structure");
            try {
                buildTreeDirectory(this.root);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
