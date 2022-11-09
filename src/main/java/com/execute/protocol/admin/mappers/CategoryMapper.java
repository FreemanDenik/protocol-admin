package com.execute.protocol.admin.mappers;


import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.dto.CategoryDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

/**
 * Преобразователь класс Category
 */
@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);
    /**
     * Преобразуем коллекцию Set {@link Category} в коллекцию Set {@link CategoryDto}
     * @param categorySet Set Category
     * @return Set CategoryDto
     */
    Set<CategoryDto> mapSetCategoryToSetDto(Set<Category> categorySet);
    /**
     * Преобразуем коллекцию Set {@link CategoryDto} в коллекцию Set {@link Category}
     * @param category Set Category
     * @return Set CategoryDto
     */
    CategoryDto mapCategoryToDto(Category category);

}
