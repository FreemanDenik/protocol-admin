package com.execute.protocol.admin;

import com.execute.protocol.admin.controllers.CommunicationController;
import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.mappers.EventMapper;
import com.execute.protocol.admin.mappers.EventTranslator;
import com.execute.protocol.admin.repositories.AnswerRepository;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.admin.repositories.EventRepository;
import com.execute.protocol.admin.services.AnswerService;
import com.execute.protocol.admin.services.CategoryService;
import com.execute.protocol.admin.services.EventService;
import com.execute.protocol.dto.AnswerDto;
import com.execute.protocol.dto.CategoryDto;
import com.execute.protocol.dto.EventDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

// Аннотация позволяет использовать аннотации BeforeAll и AfterAll в не статическом виде
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureTestDatabase

@TestPropertySource(properties = {"spring.profiles.active=test"})
class ProtocolAdminApplicationTests {
    @Autowired
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
    private EventTranslator eventTranslator;
    @Mock
    private EventMapper eventMapper;

    @InjectMocks
    @Autowired
    private CommunicationController controller;

    EventDto eventDto0;
    EventDto eventDto1;
    Event event;

    @BeforeAll
    void setEvents() {
        categoryRepository.saveAll(Arrays.asList(
                Category.builder().title("title 1").description("description 1").build(),
                Category.builder().title("title 2").description("description 2").build(),
                Category.builder().title("title 3").description("description 3").build(),
                Category.builder().title("title 4").description("description 4").build()
        ));
        this.eventDto0 = EventDto.builder()
                .category(CategoryDto.builder().id(0).build())
                .eventText("event text 1")
                .answers(new LinkedHashSet<>(Arrays.asList(
                        AnswerDto.builder().answerText("answer text 1")
                                .openCategories(Set.of(CategoryDto.builder()
                                        .id(1).build()))
                                .closeCategories(Set.of(CategoryDto.builder().
                                        id(2).build())).build(),
                        AnswerDto.builder().answerText("answer text 2")
                                .openCategories(Set.of(CategoryDto.builder()
                                        .id(2).build()))
                                .closeCategories(Set.of(CategoryDto.builder()
                                        .id(1).build())).build(),
                        AnswerDto.builder().answerText("answer text 3")
                                .openCategories(Set.of(CategoryDto.builder()
                                        .id(3).build()))
                                .closeCategories(Set.of(CategoryDto.builder()
                                        .id(4).build())).build()
                )))
                .build();
        this.eventDto1 = new EventDto(
                1,
                false,
                CategoryDto.builder().build(),
                "event text 1",
                new LinkedHashSet<>(Arrays.asList(
                        AnswerDto.builder().id(1).answerText("answer text 1")
                                .openCategories(Set.of(CategoryDto.builder()
                                        .id(1).build()))
                                .closeCategories(Set.of(CategoryDto.builder().
                                        id(2).build())).build(),
                        AnswerDto.builder().id(2).answerText("answer text 2")
                                .openCategories(Set.of(CategoryDto.builder()
                                        .id(2).build()))
                                .closeCategories(Set.of(CategoryDto.builder()
                                        .id(1).build())).build(),
                        AnswerDto.builder().id(3).answerText("answer text 3")
                                .openCategories(Set.of(CategoryDto.builder()
                                        .id(3).build()))
                                .closeCategories(Set.of(CategoryDto.builder()
                                        .id(4).build())).build()
                )));

       // controller = new CommunicationController(categoryService, categoryRepository, eventService, answerService, eventMapper);
    }

    @Test
    void createEvent() {

        controller.create(this.eventDto0);
        var result = eventRepository.findAll().get(0);
        assertEquals(result.getAnswers().size(), 3);
        assertNotNull(result.getAnswers());
        assertEquals(result.getAnswers().stream().findFirst().get().getAnswerText(), "answer text 1");
        assertEquals(result.getAnswers().stream().skip(1).findFirst().get().getAnswerText(), "answer text 2");
        assertEquals(result.getAnswers().stream().skip(2).findFirst().get().getAnswerText(), "answer text 3");

        this.eventDto0.setAnswers(result.getAnswers().stream().map(w ->
                        AnswerDto.builder()
                                .id(w.getId())
                                .answerText(w.getAnswerText())
                                .build())
                .collect(Collectors.toSet()));
        this.eventDto0.getAnswers().stream().filter(w -> w.getId() == 2).findFirst().ifPresent(w -> w.setAnswerText("answer text 22"));

        controller.create(this.eventDto0);
        var result2 = eventRepository.findAll().get(0);
        assertEquals(result2.getAnswers().size(), 3);
        assertEquals(result2.getAnswers().stream().findFirst().get().getAnswerText(), "answer text 1");
        assertEquals(result2.getAnswers().stream().skip(1).findFirst().get().getAnswerText(), "answer text 22");
        assertEquals(result2.getAnswers().stream().skip(2).findFirst().get().getAnswerText(), "answer text 3");


        //eventRepository.deleteAll();

    }

//    @Test
//    void deleteEvent() {
//        eventRepository.deleteAll();
//        createEvent();
//
//        Set<AnswerDto> copyAnswers = new LinkedHashSet<>(answers);
//        copyAnswers.removeIf(w -> w.getAnswerText().equals("answer text 2"));
//        EventDto event = EventDto.builder()
//                .id(1)
//                .category(CategoryDto.builder().id(1).build())
//                .eventText("event text").answers(copyAnswers).build();
//        controller.create(event);
//        var result = eventRepository.findAll().get(0);
//
//        assertEquals(result.getAnswers().size(), 2);
//        assertEquals(result.getAnswers().stream().findFirst().get().getAnswerText(), "answer text 1");
//        assertEquals(result.getAnswers().stream().skip(1).findFirst().get().getAnswerText(), "answer text 3");
//
//    }
//
//    @Test
//    void changeEvent() {
//        eventRepository.deleteAll();
//        createEvent();
//
//        Set<AnswerDto> copyAnswers = new LinkedHashSet<>(answers);
//        copyAnswers.stream().findFirst().get().setAnswerText("answer text 11");
//        copyAnswers.stream().skip(1).findFirst().get().setAnswerText("answer text 22");
//        copyAnswers.stream().skip(2).findFirst().get().setAnswerText("answer text 33");
//        EventDto event = EventDto.builder()
//                .id(1)
//                .category(CategoryDto.builder().id(1).build())
//                .eventText("event text").answers(copyAnswers).build();
//        controller.create(event);
//        var result = eventRepository.findAll().get(0);
//        assertEquals(result.getAnswers().size(), 3);
//        assertEquals(result.getAnswers().stream().findFirst().get().getAnswerText(), "answer text 11");
//        assertEquals(result.getAnswers().stream().skip(1).findFirst().get().getAnswerText(), "answer text 22");
//        assertEquals(result.getAnswers().stream().skip(2).findFirst().get().getAnswerText(), "answer text 33");
//
//
//    }


}
