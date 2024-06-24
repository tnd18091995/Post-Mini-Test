package jintran.postai.repository;

import jintran.postai.model.post.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface IPostRepository extends CrudRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByTitleContaining(Pageable pageable, String name);
}
