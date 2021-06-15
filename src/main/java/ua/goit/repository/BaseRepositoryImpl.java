package ua.goit.repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import ua.goit.model.BaseEntity;

public class BaseRepositoryImpl<E extends BaseEntity<ID>, ID> implements BaseRepository<E, ID> {

    private final ConcurrentHashMap<ID, E> repository = new ConcurrentHashMap<>();

    @Override
    public E save(E entity) {
        if (entity == null) throw new RuntimeException("It's impossible to save an entity with a value 'null'");
        else return repository.put(entity.getId(), entity);
    }

    @Override
    public void deleteById(ID id) {
        if (id!=null) repository.remove(id);
    }

    @Override
    public Collection<E> findAll() {
        return repository.values();
    }

    @Override
    public E getOne(ID id) {
        return findById(id).map(e -> e).orElseThrow(()-> new RuntimeException("Entity with id " + id + " not found"));
    }

    @Override
    public void deleteAll() {
        repository.clear();
    }

    @Override
    public long count() {
        return repository.size();
    }

    @Override
    public boolean existsById(ID id) {
        if (id==null) return false;
        return repository.containsKey(id);
    }

    @Override
    public Optional<E> findById(ID id) {
        if (id == null) return Optional.empty();
        return repository.containsKey(id) ? Optional.of(repository.get(id)) : Optional.empty();
    }

    @Override
    public List<E> saveAll(Iterable<E> itrbl) {
        final List<E> result = new ArrayList<>();
        if (itrbl==null) return result;
        for (E e : itrbl) result.add(this.save(e));
        return result;
    }

}
