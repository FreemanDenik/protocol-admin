package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.dto.EventDto;

public interface EventService {
    void save(Event event);
    int save(Event event, int answerId);
    Event getEvent(int id);
}
