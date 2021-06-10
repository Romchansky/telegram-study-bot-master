
package ua.goit.repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import ua.goit.model.BaseEntity;
import ua.goit.repository.BaseRepository;

public class BaseRepositoryImpl<E extends BaseEntity<ID>, ID> implements BaseRepository<E, ID> {

    private final ConcurrentHashMap<ID, E> studyBlockStateStorage = new ConcurrentHashMap<>();

    @Override
    public Optional<E> getById(ID id) {
        return studyBlockStateStorage.containsKey(id) ? Optional.of(studyBlockStateStorage.get(id)) : Optional.empty();
    }

    @Override
    public E save(E entity) {
        return studyBlockStateStorage.put(entity.getId(), entity);
    }

    @Override
    public void saveAll(Iterable<E> itrb) {
        itrb.forEach(this::save);
    }

    @Override
    public void deleteById(ID id) {
        studyBlockStateStorage.remove(id);
    }

    /*
    public StudyBlockState getUserStudyBlockStateByChatId(Long chatId) {
        return studyBlockStateStorage.get(chatId);
    }

    public void updateUserStudyBlockState(Long chatId, int currentProgress) {
        studyBlockStateStorage.computeIfPresent(chatId, (keyLong, studyBlockState) -> {
         //   studyBlockState.setCurrentProgress(currentProgress);
            return studyBlockState;
        });
    }

    public void getUserStudyBlockStateName(Long chatId) {
 //       return studyBlockStateStorage.get(chatId).getStudyBlockName();
    }
    */

}