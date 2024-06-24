package jintran.postai.service;

import jintran.postai.model.category.Category;
import jintran.postai.repository.ICategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Override
    public Iterable<Category> findAll() {
        return iCategoryRepository.findAll();
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public void save(Category category) {
        iCategoryRepository.save(category);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return iCategoryRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        iCategoryRepository.deleteById(id);
    }
}
