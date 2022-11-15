package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.entities.Thing;
import com.execute.protocol.dto.CategoryDto;
import com.execute.protocol.dto.ThingDto;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Set;

public interface ThingService {
    Page<Thing> getThingsBySearchAndWithExcludes(BooleanBuilder predicates, PageRequest pade);
}
