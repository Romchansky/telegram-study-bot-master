package ua.goit.repository;

import java.util.Optional;
import ua.goit.model.BaseEntity;

public interface BaseRepository<E extends BaseEntity<ID>, ID> {
    Optional<E> getById(ID id);
    E save(E entity);
    void saveAll(Iterable<E>itrb);
    void deleteById (ID id);
}