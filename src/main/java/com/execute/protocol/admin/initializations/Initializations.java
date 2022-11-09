package com.execute.protocol.admin.initializations;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * Класс стартовой инициализации данных базы
 */
@Component
public class Initializations {
    private final CategoryRepository categoryRepository;
    @Autowired
    public Initializations(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * Инициализируем список категории
     */
    @Bean("InitCategories")
    public void initCategories(){
        categoryRepository.saveAll(
                List.of(
                        Category.builder().title("Келен подбрасывает записку").description("описание Келен подбрасывает записку").build(),
                        Category.builder().title("Врывается стража").description("описание Врывается стража").build(),
                        Category.builder().title("Пьяная компания шумит странно").description("описание Пьяная компания шумит").build(),
                        Category.builder().title("Странник в углу передал записку").description("описание Странник в углу передал записку").build()
                ));
    }
}
