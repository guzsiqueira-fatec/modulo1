package br.gov.sp.fatec.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {
    Optional<T> findById(String id);
    List<T> findAll();
    List<T> findAllPaged(int offset, int limit);
    T save(T entity);
    void deleteById(String id);
}
