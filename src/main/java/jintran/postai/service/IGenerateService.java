package jintran.postai.service;

import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.Optional;

public interface IGenerateService <T> {
    Iterable<T> findAll();
    Page<T> findAll(Pageable pageable);

    void save(T t);

    Optional<T> findById(Long id);


    void remove(Long id);
}
