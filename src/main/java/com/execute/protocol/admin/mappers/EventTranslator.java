package com.execute.protocol.admin.mappers;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * Вспомогательный класс для Mapper (mapstruct) в котором указано,
 * правила(методы) привидения типов
 */
@Component
public class EventTranslator {
    public final CategoryRepository repository;
    @Autowired
    public EventTranslator(CategoryRepository repository) {
        this.repository = repository;
    }


    /**
     * Преобразуем Set Integer в Set Category обращаясь в базу по Set Integer
     * @param integers Set Integer
     * @return Set CategoryDto
     */
    public Set<CategoryDto> mapSetIntegerToSetCategoryDtoDI(Set<Integer> integers) {
        return CategoryMapper.INSTANCE.mapSetCategoryToSetDto(repository.findByIdIn(integers));
    }

    /**
     * Преобразуем Integer в Category обращаясь в базу по Integer
     * @param integer Integer
     * @return CategoryDto
     */
    public CategoryDto mapIntegerToCategoryDtoDI(Integer integer) {
        Optional<Category> category = repository.findById(integer);
        return category.isEmpty() ? null : CategoryMapper.INSTANCE.mapCategoryToDto(category.get());
    }
    /**
     * Преобразуем Set Category в Set Integer
     * @param categories Set Category
     * @return Set Integer
     */
    public Set<Integer> mapSetCategoryDtoToSetIntegerById(Set<CategoryDto> categories) {
        return categories.stream().mapToInt(w -> w.getId()).boxed().collect(Collectors.toSet());
    }
    /**
     * Преобразуем Category в Integer
     * @param category Category
     * @return Set Integer
     */
    public Integer mapCategoryDtoToIntegerById(CategoryDto category) {
        return category.getId();
    }
}
