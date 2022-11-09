package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.dto.CategoryDto;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Set;
import java.util.function.Predicate;

public interface CategoryService {
    Category save(CategoryDto categoryDto);
    Page<Category> getCategoriesBySearchAndWithExcludes(BooleanBuilder predicates, PageRequest pade);
    Category getCategoryById(int categoryId);
    Set<Category> getByListId(Set<Integer> categoriesId);
}
