package project.repository;

import java.util.List;

public interface CrudRepository<T, ID> {
    void save(T t);

    T findById(ID id);

    List<T> getAll();
}
