package datalakeuploader;

public class FileAlreadyUploadedException extends Exception {

    public FileAlreadyUploadedException(String message) {
        super(message);
    }
}
