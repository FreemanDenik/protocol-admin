package com.execute.protocol.admin.services;

import com.execute.protocol.admin.interfaces.FastFiner;
import org.springframework.data.domain.Page;

import java.util.Set;

/**
 * Единый интерфейс для поиска однотипных данных таких как Category и Thing
 * @param <T>
 */
public interface FastFinerService<T> {
    Page<? extends FastFiner> getBySearchAndWithExcludes(String search, Set<Integer> excludes, int page, int pageSize);
}
