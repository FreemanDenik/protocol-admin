package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Answer;
import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.mappers.EventMapper;
import com.execute.protocol.admin.repositories.AnswerRepository;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.admin.repositories.EventRepository;
import com.execute.protocol.dto.EventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final AnswerRepository answerRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    private final EntityManager entityManager;
    @Autowired
    public EventServiceImpl(EventRepository eventRepository, AnswerRepository answerRepository, CategoryRepository categoryRepository, EventMapper eventMapper, EntityManager entityManager) {
        this.eventRepository = eventRepository;
        this.answerRepository = answerRepository;
        this.categoryRepository = categoryRepository;
        this.eventMapper = eventMapper;
        this.entityManager = entityManager;
    }

    /**
     * Сохранить событие
     * @param event сущность событие
     * @return Event
     */
    public Event save(Event event){
        event.setCreateTime(LocalDateTime.now());
        event.setUpdateTime(LocalDateTime.now());
        event.syncAnswers();
        eventRepository.save(event);
        return event;
    }

    /**
     * Сохранить событие из {@link EventDto} модели,
     * преобразование mapstruct
     * @param eventDto
     * @return Event
     */
    public Event saveFromDto(EventDto eventDto) {
        Event event = eventMapper.mapEventFromDto(eventDto);

        // Задаем время создания
        event.setCreateTime(LocalDateTime.now());
        event.setUpdateTime(LocalDateTime.now());
        event.syncAnswers();
        eventRepository.save(event);
        return event;
    }

    /**
     * Обновить модель {@link Event} данными из модели {@link EventDto}<br>
     * преобразование mapstruct<br>
     * В процессе удаляются или добавляются {@link Answer}<br>
     * Так же обновляются категории связанных (сюжетной связкой parent child) сущности {@link Event}
     * @param eventDto
     * @return Event
     */
    @Transactional
    public Event updateFromDto(EventDto eventDto) {
       Event event = this.getEvent(eventDto.getId());
       int categoryId = event.getCategory();
        // Удаление из Event.Answer записей которых нет в EventDto.AnswerDto
        // выражение использовать до Mapper преобразовании
        event.getAnswers()
                .stream()
                .filter(w ->
                        !eventDto.getAnswers().stream().mapToInt(e -> e.getId())
                                .boxed().collect(Collectors.toSet())
                                .contains(w.getId()))
                .collect(Collectors.toSet())
                .forEach(event::removeAnswer);
        // Обновление (преобразование mapstruct) event данными из eventDto
        eventMapper.mapUpdateEventFromDto(eventDto, event);
        event.setUpdateTime(LocalDateTime.now());
        // Синхронизация Answer записей с Event
        event.syncAnswers();
        eventRepository.save(event);
        // Если при обновлении была изменена категория то,
        // меняется категория всех связанных (сюжетной связкой parent child) сущностей Event
        if (categoryId != event.getCategory()){
            Set<Event> result = eventRepository.findByCategoryAndChild(categoryId, true);
            if (!result.isEmpty()) {
                result.forEach(w -> w.setCategory(event.getCategory()));
                eventRepository.saveAll(result);
            }
        }
        return event;
    }

    /**
     * Создание пустого события {@link Event} с
     * привязкой его к ответу {@link Answer} переданного (по сути предыдущего) {@link Event}
     * @param event событие к котором находиться целевой ответ Answer
     * @param answerId id ответа
     * @return
     */
    @Transactional
    public int saveAndCreateEventLink(Event event, int answerId) {
        Event newEvent = Event.builder()
                .category(event.getCategory())
                .parentEvent(event.getParentEvent() == 0 ? event.getId() : event.getParentEvent())
                .ownEvent(event.getId())
                .ownAnswer(answerId)
                .child(true)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .eventText("-- Новое связанное событие --")
                .answers(
                        new LinkedHashSet<>(Arrays.asList(
                                Answer.builder().answerText("-- Текст первого ответа --")

                                        .ifThings(new HashSet<>())
                                        .giveThings(new HashSet<>())
                                        .openCategories(new HashSet<>())
                                        .closeCategories(new HashSet<>())
                                        .build()
                        ))
                ).build();
        save(newEvent);
        event.getAnswers().stream().filter(w -> w.getId() == answerId).findFirst().get().setLinkEvent(newEvent.getId());
        save(event);
        return newEvent.getId();
    }

    public Event getEvent(int id) {
        return eventRepository.findById(id).get();

    }
}
