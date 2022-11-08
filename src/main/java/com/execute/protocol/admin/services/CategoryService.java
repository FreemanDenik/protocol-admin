package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.dto.CategoryDto;

public interface CategoryService {
    Category save(CategoryDto categoryDto);
}
