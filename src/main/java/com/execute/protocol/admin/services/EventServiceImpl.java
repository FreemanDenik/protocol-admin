package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Answer;
import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.mappers.EventMapper;
import com.execute.protocol.admin.repositories.AnswerRepository;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.admin.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class EventServiceImpl implements EventService {
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
     *
     * @param event сущность событие
     */
    public void save(Event event) {
        // Метод syncAnswers добавляет всем answers
        // Родительский метод Event

        event.syncAnswers();
        eventRepository.save(event);
        //answerRepository.saveAll(event.getAnswers());

    }

    public int save(Event event, int answerId) {
        // Метод syncAnswers добавляет всем answers
        // Родительский метод Event
        Set<Answer> answers = new LinkedHashSet<>(Arrays.asList(
                Answer.builder().answerText("-- Текст первого ответа --")
                        .ifThings(new HashSet<>())
                        .giveThings(new HashSet<>())
                        .openCategories(new HashSet<>())
                        .closeCategories(new HashSet<>())
                        .build()
        ));
        Event newEvent = Event.builder()
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .eventText("-- Новое связанное событие --")
                .answers(
                        answers
                ).build();
        newEvent.syncAnswers();
        answerRepository.saveAll(answers);
        eventRepository.save(newEvent);
        //answerRepository.saveAll(newEvent.getAnswers());
        event.getAnswers().stream().filter(w -> w.getId() == answerId).findFirst().get().setLink(newEvent.getId());
        save(event);
        return newEvent.getId();
    }

    public Event getEvent(int id) {
        return eventRepository.findById(id).get();

    }
}
