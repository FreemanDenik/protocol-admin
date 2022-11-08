package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Создает новую категорию
     *
     * @param categoryDto создаваемая категория
     * @return id - созданной категории
     */
    public Category save(CategoryDto categoryDto) {
        Category category = Category.builder().title(categoryDto.getTitle()).description(categoryDto.getDescription()).build();
        categoryRepository.save(category);
        return category;
    }
}
