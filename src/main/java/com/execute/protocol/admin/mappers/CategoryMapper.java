package com.execute.protocol.admin.mappers;


import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.interfaces.FastFiner;
import com.execute.protocol.dto.CategoryDto;
import com.execute.protocol.dto.ThingDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

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
    Set<CategoryDto> mapSetCategoryToSetCategoryDto(Set<Category> categorySet);
    /**
     * Преобразуем коллекцию Set {@link CategoryDto} в коллекцию Set {@link Category}
     * @param category Set Category
     * @return Set CategoryDto
     */
    CategoryDto mapCategoryToCategoryDto(Category category);
//    /**
//     * Преобразуем коллекцию Set {@link FastFiner} в коллекцию Set {@link CategoryDto}
//     * @param fastFiner Set FastFiner
//     * @return Set CategoryDto
//     */
//    Set<CategoryDto> mapSetFastFinerToSetCategoryDto(Set<? extends FastFiner> fastFiner);

}
