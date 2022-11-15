package com.execute.protocol.admin.repositories;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.entities.Thing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ThingRepository extends JpaRepository<Thing, Integer>, QuerydslPredicateExecutor<Thing> {
   // Set<Category> findByTitleContains(String partTitle);
    Set<Thing> findByTitleContainsAndIdNotIn(String search, Set<Integer> excludes);
    Set<Thing> findByIdIn(Set<Integer> ids);
}
