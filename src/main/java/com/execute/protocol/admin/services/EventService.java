package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.dto.EventDto;

public interface EventService {
    Event save(Event event);
    Event saveFromDto(EventDto event);
    Event updateFromDto(EventDto event);
    int saveAndCreateEventLink(Event event, int answerId) ;
    Event getEvent(int id);
}
