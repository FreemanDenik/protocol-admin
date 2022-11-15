package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.entities.Thing;
import com.execute.protocol.admin.repositories.ThingRepository;
import com.execute.protocol.dto.CategoryDto;
import com.execute.protocol.dto.ThingDto;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class ThingServiceImpl implements ThingService {
    private final ThingRepository thingRepository;

    public ThingServiceImpl(ThingRepository thingRepository) {
        this.thingRepository = thingRepository;
    }
    /**
     * Получаем результаты по поиску части названия (contains)
     * а так отфильтровываем уже существующие и пагинируем ответ
     *
     * @param predicates BooleanBuilder
     * @param pade       PageRequest
     * @return Page Category
     */
    public Page<Thing> getThingsBySearchAndWithExcludes(BooleanBuilder predicates, PageRequest pade) {
        return thingRepository.findAll(predicates, pade);
    }

}
