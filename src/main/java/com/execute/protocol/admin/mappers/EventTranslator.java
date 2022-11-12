package com.execute.protocol.admin.mappers;

import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.dto.CategoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public Set<CategoryDto> mapSetIntegersToSetCategoryDtoDI(Set<Integer> integers) {
        return CategoryMapper.INSTANCE.mapSetCategoryToSetDto(repository.findByIdIn(integers));
    }

    /**
     * Преобразуем Set Category в Set Integer
     * @param categories Set Category
     * @return Set Integer
     */
    public Set<Integer> mapSetCategoryDtoToSetIntegerById(Set<CategoryDto> categories) {
        return categories.stream().mapToInt(w -> w.getId()).boxed().collect(Collectors.toSet());
    }
}
