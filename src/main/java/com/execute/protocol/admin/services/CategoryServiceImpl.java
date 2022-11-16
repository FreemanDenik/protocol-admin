package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.entities.QCategory;
import com.execute.protocol.admin.interfaces.FastFiner;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.querydsl.core.BooleanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService, FastFinerService<Category> {
    private final CategoryRepository categoryRepository;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
        QCategory qCategory = QCategory.category;
        // Сортируем по строке поиска
        Optional.ofNullable(search)
                .map(qCategory.title::contains).map(predicates::and);
        // Сортируем по исключающим категориям
        // условие добавляется только если excludes != null
        Optional.ofNullable(excludes)
                .map(qCategory.id::in).map(predicates::andNot);
        return categoryRepository.findAll(predicates, PageRequest.of(page, pageSize));
    }

}
