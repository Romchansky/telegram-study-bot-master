package ua.goit.repository;

import ua.goit.model.BaseEntity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RepositoryFactory {

    private final static Map<String, BaseRepository> REPOSITORIES  = new ConcurrentHashMap<>();

    public synchronized static <E extends BaseEntity<ID>, R extends BaseRepository<E,ID>, ID> BaseRepository<E, ID> of (Class<E> modelClass) {
        final String modelName = modelClass.getName();
        if (!REPOSITORIES.containsKey(modelName)) REPOSITORIES.put(modelName, new BaseRepositoryImpl<>());
        return REPOSITORIES.get(modelName);
    }

}