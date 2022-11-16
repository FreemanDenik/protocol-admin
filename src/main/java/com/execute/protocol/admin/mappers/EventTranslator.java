package com.execute.protocol.admin.mappers;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.entities.Thing;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.admin.repositories.ThingRepository;
import com.execute.protocol.dto.CategoryDto;
import com.execute.protocol.dto.ThingDto;
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
    public final CategoryRepository categoryRepository;
    public final ThingRepository thingRepository;
    @Autowired
    public EventTranslator(CategoryRepository categoryRepository, ThingRepository thingRepository) {
        this.categoryRepository = categoryRepository;
        this.thingRepository = thingRepository;
    }
    /**
     * Преобразуем Set Integer в Set Category обращаясь в базу по Set Integer
     * @param integers Set Integer
     * @return Set CategoryDto
     */
 
    public Set<CategoryDto> mapSetIntegerToSetCategoryDtoDI(Set<Integer> integers) {
        return CategoryMapper.INSTANCE.mapSetCategoryToSetCategoryDto(categoryRepository.findByIdIn(integers));
    }  
    public Set<ThingDto> mapSetIntegerToSetThingDtoDI(Set<Integer> integers) {
        return ThingMapper.INSTANCE.mapSetThingToSetThingDto(thingRepository.findByIdIn(integers));
    }
    /**
     * Преобразуем Integer в Category обращаясь в базу по Integer
     * @param integer Integer
     * @return CategoryDto
     */
    public CategoryDto mapIntegerToCategoryDtoDI(Integer integer) {
        Optional<Category> category = categoryRepository.findById(integer);
       // return Optional.ofNullable(category.isEmpty()).map(w->CategoryMapper.INSTANCE.mapCategoryToCategoryDto(w.get())).orElse(null);
        return category.isEmpty() ? null : CategoryMapper.INSTANCE.mapCategoryToCategoryDto(category.get());
    }
//    /**
//     * Преобразуем Integer в Thing обращаясь в базу по Integer <br>
//     * @param integer Integer
//     * @return ThingDto
//     */
//    public ThingDto mapIntegerToThingDtoDI(Integer integer) {
//        Optional<Thing> thing = thingRepository.findById(integer);
//        return thing.isEmpty() ? null : ThingMapper.INSTANCE.mapThingToThingDto(thing.get());
//    }
    /**
     * Преобразуем Set Category в Set Integer
     * @param categoriesDto Set Category
     * @return Set Integer
     */
    public Set<Integer> mapSetCategoryDtoToSetIntegerById(Set<CategoryDto> categoriesDto) {
        return categoriesDto.stream().mapToInt(w -> w.getId()).boxed().collect(Collectors.toSet());
    }
    /**
     * Преобразуем Set Thing в Set Integer
     * @param thingsDto Set Category
     * @return Set Integer
     */
    public Set<Integer> mapSetThingDtoToSetIntegerById(Set<ThingDto> thingsDto) {
        return thingsDto.stream().mapToInt(w -> w.getId()).boxed().collect(Collectors.toSet());
    }
    /**
     * Преобразуем Category в Integer
     * @param category Category
     * @return Set Integer
     */
    public Integer mapCategoryDtoToIntegerById(CategoryDto category) {
        return category.getId();
    }
//    /**
//     * Преобразуем Thing в Integer <br>
//     * @param thingDto Category
//     * @return Set Integer
//     */
//    public Integer mapThingDtoToIntegerById(ThingDto thingDto) {
//        return thingDto.getId();
//    }

}
