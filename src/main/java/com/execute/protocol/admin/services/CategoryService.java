package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.dto.CategoryDto;

import java.util.Set;

public interface CategoryService {
    Category save(CategoryDto categoryDto);
    Set<Category> getCategoriesBySearchAndWithExcludes(String search, Set<Integer> excludesId);
    Category getCategoryById(int categoryId);
    Set<Category> getByListId(Set<Integer> categoriesId);
}
