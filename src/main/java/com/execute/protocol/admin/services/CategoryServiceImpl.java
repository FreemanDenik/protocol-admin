package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.dto.CategoryDto;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Predicate;

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
    /**
     * Получаем результаты по поиску части названия (contains)
     * @param search - часть названия
     * @return List Category
     */
    public Set<Category> getCategoriesBySearchAndWithExcludes(String search, Set<Integer> excludesId){


        return categoryRepository.findByTitleContainsAndIdNotIn(search, excludesId);
    }
    /**
     * Получаем категорию по id
     * @param categoryId - id
     * @return Category
     */
    public Category getCategoryById(int categoryId){
        return categoryRepository.findById(categoryId).orElse(null);
    }
    public Set<Category> getByListId(Set<Integer> categoriesId){
        return categoryRepository.findByIdIn(categoriesId);
    }
}
