package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.QCategory;
import com.execute.protocol.admin.entities.QThing;
import com.execute.protocol.admin.interfaces.FastFiner;
import com.execute.protocol.admin.entities.Thing;
import com.execute.protocol.admin.repositories.ThingRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ThingServiceImpl implements ThingService, FastFinerService<Thing> {
    private final ThingRepository thingRepository;


    public ThingServiceImpl(ThingRepository thingRepository) {
        this.thingRepository = thingRepository;

    }
    /**
     * Получаем результаты по поиску части названия,
     * а так же отфильтровываем уже существующие и пагинируем ответ
     *
     * @param search   поисковая строка
     * @param excludes коллекция id исключении
     * @param page     номер страница
     * @param pageSize количество записей на странице
     * @return
     */
    public Page<? extends FastFiner> getBySearchAndWithExcludes(
            String search,
            Set<Integer> excludes,
            int page,
            int pageSize) {
        BooleanBuilder predicates = new BooleanBuilder();
        QThing qThing = QThing.thing;
        // Сортируем по строке поиска
        Optional.ofNullable(search)
                .map(qThing.title::contains).map(predicates::and);
        // Сортируем по исключающим категориям
        // условие добавляется только если excludes != null
        Optional.ofNullable(excludes)
                .map(qThing.id::in).map(predicates::andNot);
        return thingRepository.findAll(predicates, PageRequest.of(page, pageSize));
    }

}
