package repository;

import java.util.Optional;

public interface CrudRepository<Entity, Key> {

    void save(Entity entity);

    Optional<Entity> findOne(Key key);
}
