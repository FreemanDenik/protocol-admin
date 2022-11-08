package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Answer;
import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.repositories.AnswerRepository;
import com.execute.protocol.admin.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class EventServiceImpl implements EventService{
    private final EventRepository eventRepository;
    private final AnswerRepository answerRepository;
    @Autowired
    public EventServiceImpl(EventRepository eventRepository, AnswerRepository answerRepository) {
        this.eventRepository = eventRepository;
        this.answerRepository = answerRepository;
    }

    /**
     * Сохранить событие
      * @param event сущность событие
     */
    @Transactional
    public void save(Event event){
        eventRepository.save(event);
        List<Answer> answer = event.getAnswers();
        answer.forEach(e -> {
            e.setEvent(event);
        });

        answerRepository.saveAll(answer);
    }
    public Event getEvent(int id){
       return eventRepository.findById(id).get();

    }
}
