package broker;

public interface Consumer<Key> {
    Key consume();
}
