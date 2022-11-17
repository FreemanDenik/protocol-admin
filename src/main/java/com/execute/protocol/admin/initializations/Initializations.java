package com.execute.protocol.admin.initializations;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.entities.Thing;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.admin.repositories.ThingRepository;
import com.execute.protocol.enums.EmTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Класс стартовой инициализации данных базы
 */
@Component
@Profile("dev")
public class Initializations {
    private final CategoryRepository categoryRepository;
    private final ThingRepository thingRepository;

    @Autowired
    public Initializations(CategoryRepository categoryRepository, ThingRepository thingRepository) {
        this.categoryRepository = categoryRepository;
        this.thingRepository = thingRepository;
    }

    /**
     * Инициализируем список категории
     */
    @Bean("InitCategories")
    public void initCategories() {
        categoryRepository.saveAll(
                List.of(
                        Category.builder().title("Келен подбрасывает записку").description("описание Келен подбрасывает записку").build(),
                        Category.builder().title("Врывается стража").description("описание Врывается стража").build(),
                        Category.builder().title("Пьяная компания шумит странно").description("описание Пьяная компания шумит").build(),
                        Category.builder().title("Странник в углу передал записку").description("описание Странник в углу передал записку").build()
                ));
        thingRepository.saveAll(
                List.of(
                        Thing.builder().title("Серебряная монета золото +20").description("описание Серебряная монета спасает от полного банкродства").target(EmTarget.GOLD).count(20).build(),
                        Thing.builder().title("Глупая шутка -50").description("описание Не приятный случей влияние -50").target(EmTarget.INFLUENCE).count(-50).build(),
                        Thing.builder().title("Поддержка окружающих репутация +1").description("описание Поддержка окружающих репутация +1").target(EmTarget.REPUTATION).count(1).build()
                )
        );
    }
}
