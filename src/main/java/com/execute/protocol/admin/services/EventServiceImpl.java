package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.mappers.CategoryMapper;
import com.execute.protocol.admin.mappers.EventMapper;
import com.execute.protocol.admin.repositories.AnswerRepository;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.admin.repositories.EventRepository;
import com.execute.protocol.dto.AnswerDto;
import com.execute.protocol.dto.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class EventServiceImpl implements EventService{
    private final EventRepository eventRepository;
    private final AnswerRepository answerRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;
    @Autowired
    public EventServiceImpl(EventRepository eventRepository, AnswerRepository answerRepository, CategoryRepository categoryRepository, EventMapper eventMapper) {
        this.eventRepository = eventRepository;
        this.answerRepository = answerRepository;
        this.categoryRepository = categoryRepository;
        this.eventMapper = eventMapper;
    }

    /**
     * Сохранить событие
      * @param event сущность событие
     */

    public void save(Event event){
        // Метод syncAnswers добавляет всем answers
        // Родительский метод Event
        event.syncAnswers();
        eventRepository.save(event);
    }
    public Event getEvent(int id){
       return eventRepository.findById(id).get();

    }
}
