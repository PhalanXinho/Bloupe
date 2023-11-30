package broker;

public interface Producer<Key> {
    void produce(Key key);
}
