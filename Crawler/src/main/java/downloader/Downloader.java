package downloader;

public interface Downloader<R,K> {
    R download(K key) throws BookNotFoundException;
}
