package com.execute.protocol.admin.controllers;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.entities.Thing;
import com.execute.protocol.admin.interfaces.FastFiner;
import com.execute.protocol.admin.services.FastFinerService;
import com.execute.protocol.dto.SearchFinerDto;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("finer")
public class FinerController {
    private final FastFinerService<Category> fastFinerCategoryService;
    private final FastFinerService<Thing> fastFinerThingService;

    public FinerController(FastFinerService<Category> fastFinerCategoryService, FastFinerService<Thing> fastFinerThingService) {
        this.fastFinerCategoryService = fastFinerCategoryService;
        this.fastFinerThingService = fastFinerThingService;
    }

    /**
     * Контроллер получения категории по части названия
     * @param searchFinerDto - краткие сведения по быстрому поиску и исключению
     * @return Set CategoryDto
     */
    @PostMapping(value = "/category")
    public Set<? extends FastFiner> category(@RequestBody SearchFinerDto searchFinerDto) {
        int page = 0;
        int pageSize = 10;
        // Обращаемся в методу через единый интерфейс и получаем единый ответ FastFiner
        Page<? extends FastFiner> pageCategories = fastFinerCategoryService.getBySearchAndWithExcludes(searchFinerDto.getSearch(), searchFinerDto.getExcludes(), page, pageSize);
        // Преобразуем список категории в CategoryDto список
        //var ttt = CategoryMapper.INSTANCE.mapSetFastFinerToSetCategoryDto(pageCategories.toSet());
        return pageCategories.toSet();
    }
    /**
     * Контроллер получения предметов по части названия
     * @param fastFinerDto - краткие сведения по быстрому поиску и исключению
     * @return Set ThingDto
     */
    @PostMapping(value = "/thing")
    public Set<? extends FastFiner> thing(@RequestBody SearchFinerDto fastFinerDto) {
        int page = 0;
        int pageSize = 10;
        // Обращаемся в методу через единый интерфейс и получаем единый ответ FastFiner
        Page<? extends FastFiner> pageThings = fastFinerThingService.getBySearchAndWithExcludes(fastFinerDto.getSearch(), fastFinerDto.getExcludes(), page, pageSize);
        // Преобразуем список предметов в Dto список
        return pageThings.toSet();
    }
}
