package datalakeuploader;

public interface Uploader<K> {
    void upload(K object) throws FileAlreadyUploadedException;
}
