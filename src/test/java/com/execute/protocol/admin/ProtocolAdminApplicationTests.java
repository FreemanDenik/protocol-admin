package com.execute.protocol.admin;

import com.execute.protocol.admin.annotations.AnnotationDisabledBean;
import com.execute.protocol.admin.controllers.CommunicationController;
import com.execute.protocol.admin.entities.Answer;
import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.mappers.EventMapper;
import com.execute.protocol.admin.repositories.AnswerRepository;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.admin.repositories.EventRepository;
import com.execute.protocol.admin.services.AnswerService;
import com.execute.protocol.admin.services.CategoryService;
import com.execute.protocol.admin.services.EventService;
import com.execute.protocol.dto.AnswerDto;
import com.execute.protocol.dto.EventDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

// Аннотация позволяет использовать аннотации BeforeAll и AfterAll в не статическом виде
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureTestDatabase

@TestPropertySource(properties = {"NO_TEST=true"})
class ProtocolAdminApplicationTests {
    @Autowired
    @AnnotationDisabledBean
    private EventRepository eventRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private EventService eventService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private EventMapper eventMapper;
    private CommunicationController controller;

    @BeforeAll
    void setEvents() {
//        eventRepository.saveAll(
//                List.of(
//                        Event.builder().question("question 1").updateTime(LocalDateTime.now().plusSeconds(1)).createTime(LocalDateTime.now()).build(),
//                        Event.builder().question("question 2").updateTime(LocalDateTime.now().plusSeconds(2)).createTime(LocalDateTime.now()).build(),
//                        Event.builder().question("question 3").updateTime(LocalDateTime.now().plusSeconds(3)).createTime(LocalDateTime.now()).build(),
//                        Event.builder().question("question 4").updateTime(LocalDateTime.now().plusSeconds(4)).createTime(LocalDateTime.now()).build(),
//                        Event.builder().question("question 5").updateTime(LocalDateTime.now().plusSeconds(5)).createTime(LocalDateTime.now()).build()
//                )
//        );

        categoryRepository.saveAll(Arrays.asList(
                Category.builder().title("title 1").description("description 1").build(),
                Category.builder().title("title 2").description("description 2").build(),
                Category.builder().title("title 3").description("description 3").build(),
                Category.builder().title("title 4").description("description 4").build()
        ));
        controller = new CommunicationController(categoryService, categoryRepository, eventService, answerService, eventMapper);
    }

    @Test
    void mapStruct() {
        Set<Answer> answers = new LinkedHashSet<>(Arrays.asList(
                Answer.builder().id(1).openCategories(Set.of(1,3,4)).build(),
                Answer.builder().id(2).openCategories(Set.of(1,3)).build()
        ));
       // Event event = Event.builder().id(1).eventText("sdsd").answers(answers).build();
       // var tt1 = categoryRepository.findAll();
       //var tt = eventMapper.mapEventToDto(event);
       //var size = tt.getAnswers().stream().findFirst().get().getOpenCategoriesView().size();
       //assertEquals(tt.getAnswers().stream().findFirst().get().getOpenCategoriesView().size(), 3);
    }

    @Test
    void cascadeDeleteAnswers() {
        eventRepository.deleteAll();
        answerRepository.deleteAll();
        Set<Answer> answers = new LinkedHashSet<>(Arrays.asList(
                Answer.builder().answerText("answer text 111").build(),
                Answer.builder().answerText("answer text 222").build(),
                Answer.builder().answerText("answer text 333").build()
        ));
        Event event = Event.builder().eventText("event text").createTime(LocalDateTime.now()).updateTime(LocalDateTime.now()).answers(answers).build();

        eventService.save(event);
        Event resultEvent = eventRepository.findAll().stream().findFirst().get();
        List<Answer> resultAnswer = answerRepository.findAll();

        assertEquals(resultAnswer.size(), 3);
        assertEquals(resultAnswer.get(0).getAnswerText(), "answer text 111");
        assertEquals(resultAnswer.get(1).getAnswerText(), "answer text 222");
        assertEquals(resultAnswer.get(2).getAnswerText(), "answer text 333");

        eventRepository.delete(resultEvent);
        resultAnswer = answerRepository.findAll();
        assertEquals(resultAnswer.size(), 0);

    }

    @Test
    void deleteEvent() {
        eventRepository.deleteAll();
        createEvent();

        List<Answer> a = answerRepository.findAll();
        Event e = eventRepository.findAll().stream().findFirst().get();
        Set<AnswerDto> answers = new LinkedHashSet<>(Arrays.asList(
                AnswerDto.builder().id(a.get(0).getId()).answerText("answer text 11").build(),
                //AnswerDto.builder().id(2).answerText("answer text 2").build(),
                AnswerDto.builder().id(a.get(2).getId()).answerText("answer text 33").build()
        ));
        EventDto event = EventDto.builder().id(e.getId()).eventText("event text").answers(answers).build();
        controller.create(event);
        var result = eventRepository.findAll().get(0);

        assertEquals(result.getAnswers().size(), 2);
        assertEquals(result.getAnswers().stream().findFirst().get().getAnswerText(), "answer text 11");
        assertEquals(result.getAnswers().stream().skip(1).findFirst().get().getAnswerText(), "answer text 33");

    }

    @Test
    void changeEvent() {
        eventRepository.deleteAll();
        createEvent();
        List<Answer> a = answerRepository.findAll();
        Event e = eventRepository.findAll().stream().findFirst().get();
        Set<AnswerDto> answers = new LinkedHashSet<>(Arrays.asList(
                AnswerDto.builder().id(a.get(0).getId()).answerText("answer text 11").build(),
                AnswerDto.builder().id(a.get(1).getId()).answerText("answer text 22").build(),
                AnswerDto.builder().id(a.get(2).getId()).answerText("answer text 33").build()
        ));
        EventDto event = EventDto.builder().id(e.getId()).eventText("event text").answers(answers).build();
        controller.create(event);
        var result = eventRepository.findAll().get(0);
        assertEquals(result.getAnswers().size(), 3);
        assertEquals(result.getAnswers().stream().findFirst().get().getAnswerText(), "answer text 11");
        assertEquals(result.getAnswers().stream().skip(1).findFirst().get().getAnswerText(), "answer text 22");
        assertEquals(result.getAnswers().stream().skip(2).findFirst().get().getAnswerText(), "answer text 33");


    }

    @Test
    void createEvent() {
        eventRepository.deleteAll();
        Set<AnswerDto> answers = new LinkedHashSet<>(Arrays.asList(
                AnswerDto.builder().answerText("answer text 1").build(),
                AnswerDto.builder().answerText("answer text 2").build(),
                AnswerDto.builder().answerText("answer text 3").build()
        ));
        EventDto event = EventDto.builder().eventText("event text").answers(answers).build();
        controller.create(event);
        var result = eventRepository.findAll().get(0);

        assertEquals(result.getAnswers().size(), 3);
        assertNotNull(result.getAnswers());
        assertEquals(result.getAnswers().stream().findFirst().get().getAnswerText(), "answer text 1");
        assertEquals(result.getAnswers().stream().skip(1).findFirst().get().getAnswerText(), "answer text 2");
        assertEquals(result.getAnswers().stream().skip(2).findFirst().get().getAnswerText(), "answer text 3");


        //eventRepository.deleteAll();

    }


}
