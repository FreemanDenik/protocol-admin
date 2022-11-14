package com.execute.protocol.admin.services;

import com.execute.protocol.admin.entities.Answer;
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
    //@Transactional
    public void save(Event event){
        Set<Answer> answer = event.getAnswers();
        answer.forEach(e -> {
            e.setEvent(event);
        });

        eventRepository.save(event);
       // answerRepository.saveAll(answer);

    }
    public Event getEvent(int id){
/*        var tt = eventRepository.findById(id).get();
        tt.setAnswers(uu);
        return tt;*/
       return eventRepository.findById(id).get();

    }
    public EventDto getEventDto(int id){
       Event event = eventRepository.findById(id).get();
       EventDto eventDto = eventMapper.mapEventToDto(event);
       Set<AnswerDto> answers = eventDto.getAnswers();
       answers.forEach(w->{
           Set<Category> category =null; //categoryRepository.findByIdIn(w.getOpenCategories());
           w.setOpenCategories(CategoryMapper.INSTANCE.mapSetCategoryToSetDto(category));
       });
       //eventDto.setAnswers(answers);
       return eventDto;

    }
}
