package com.execute.protocol.admin.repositories;

import com.execute.protocol.admin.entities.Answer;
import com.execute.protocol.admin.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
}
