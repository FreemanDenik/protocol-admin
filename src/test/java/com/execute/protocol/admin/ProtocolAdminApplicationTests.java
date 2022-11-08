package com.execute.protocol.admin;

import com.execute.protocol.admin.controllers.CommunicationController;
import com.execute.protocol.admin.entities.Answer;
import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.mappers.EventMapper;
import com.execute.protocol.admin.repositories.AnswerRepository;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.admin.repositories.EventRepository;
import com.execute.protocol.admin.services.AnswerService;
import com.execute.protocol.admin.services.CategoryService;
import com.execute.protocol.admin.services.EventService;
import com.execute.protocol.admin.services.EventServiceImpl;
import com.execute.protocol.dto.AnswerDto;
import com.execute.protocol.dto.EventDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;

import java.net.http.HttpHeaders;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase

class ProtocolAdminApplicationTests {
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private EventService eventService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private TestRestTemplate restTemplate;

    private CommunicationController controller;
    @BeforeEach
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
        controller = new CommunicationController(categoryService, eventService, answerService);
    }
    @Test
    void changeEvent() {
        createEvent();
        List<AnswerDto> answers2 = new ArrayList<>(Arrays.asList(
                AnswerDto.builder().id(1).answerText("answer text 11").build(),
                AnswerDto.builder().id(2).answerText("answer text 2").build(),
                AnswerDto.builder().id(3).answerText("answer text 33").build()
        ));
        EventDto event2 = EventDto.builder().id(1).eventText("event text").answers(answers2).build();
        //controller.create(event2);
        var tt2= eventRepository.findAll();

    }
    @Test
    void createEvent() {
        List<AnswerDto> answers = new ArrayList<>(Arrays.asList(
                AnswerDto.builder().answerText("answer text 1").build(),
                AnswerDto.builder().answerText("answer text 2").build(),
                AnswerDto.builder().answerText("answer text 3").build()
        ));
        EventDto event = EventDto.builder().id(0).eventText("event text").answers(answers).build();
        //controller.create(event);
        var result = eventRepository.findAll();

        assertEquals(result.get(0).getAnswers().size(), 3);
        assertNotNull(result.get(0).getAnswers());
        assertEquals(result.get(0).getAnswers().get(0).getId(), 1);
        assertEquals(result.get(0).getAnswers().get(1).getId(), 2);
        assertEquals(result.get(0).getAnswers().get(2).getId(), 3);


    }
    @Test
    void contextLoads() {
        List<Answer> answers = new ArrayList<>(Arrays.asList(
                Answer.builder().answerText("answer text 1").build(),
                Answer.builder().answerText("answer text 2").build(),
                Answer.builder().answerText("answer text 3").build()
        ));
        Event event = Event.builder().eventText("event text").answers(answers).build();
        event.setCreateTime(LocalDateTime.now());
        event.setUpdateTime(LocalDateTime.now());
        eventService.save(event);
        var tt= eventRepository.findAll();


    }

}
