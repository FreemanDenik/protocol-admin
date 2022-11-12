package com.execute.protocol.admin.repositories;

import com.execute.protocol.admin.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>, QuerydslPredicateExecutor<Category> {
   // Set<Category> findByTitleContains(String partTitle);
    Set<Category> findByTitleContainsAndIdNotIn(String search, Set<Integer> excludesId);
    Set<Category> findByIdIn(Set<Integer> ids);
}
