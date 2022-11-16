package com.execute.protocol.admin.controllers;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.entities.Thing;
import com.execute.protocol.admin.interfaces.FastFiner;
import com.execute.protocol.admin.mappers.CategoryMapper;
import com.execute.protocol.admin.mappers.EventMapper;
import com.execute.protocol.admin.mappers.ThingMapper;
import com.execute.protocol.admin.services.AnswerService;
import com.execute.protocol.admin.services.EventService;
import com.execute.protocol.admin.services.FastFinerService;
import com.execute.protocol.admin.services.ThingService;
import com.execute.protocol.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "communication")
public class CommunicationController {
    private final EventService eventService;
    private final AnswerService answerService;
    private final EventMapper eventMapper;
    public CommunicationController(EventService eventService, AnswerService answerService, EventMapper eventMapper) {

        this.eventService = eventService;
        this.answerService = answerService;
        this.eventMapper = eventMapper;
    }
    /**
     * Админка
     */
    @GetMapping("/")
    public String index(ModelMap model, Principal principal) {
        model.addAttribute("name", principal.getName());
        return "communication/index";
    }
    /**
     * GET Отображение пустой/заполненной формы создания/изменения события
     *
     * @param model
     * @param eventId
     * @return View
     */
    @GetMapping(value = "/event")
    public String create(ModelMap model, @RequestParam(value = "id", defaultValue = "0") int eventId) {
        EventDto eventDto;
        if (eventId > 0) {
            Event event = eventService.getEvent(eventId);

            eventDto = eventMapper.mapEventToDto(event);
        } else {
            // Все эти пустые экземпляры создаются для того что бы
            // можно было использовать thymeleaf и проверять значения на NULL
            eventDto = new EventDto();
            // Так же пустая модель AnswerDto создается чтобы одна запись всегда была
            Set<AnswerDto> answersDto = Set.of(new AnswerDto());
            eventDto.setAnswers(answersDto);
        }

        model.addAttribute("model", eventDto);
        return "communication/event";
    }

    /**
     * POST Создание/Изменение события
     * @param eventDto
     * @return boolean
     */
    @ResponseBody
    @PostMapping(value = "/event")
    public int create(@RequestBody EventDto eventDto) {
        int eventId = eventDto.getId();
        Event event;
        // Создание события
        if (eventId == 0) {
            event = eventMapper.mapEventFromDto(eventDto);
            // Задаем время создания
            event.setCreateTime(LocalDateTime.now());
        } else {
            event = eventService.getEvent(eventId);
            // Удаление из Event.Answer записей которых нет в EventDto.AnswerDto
            // выражение использовать до Mapper преобразовании
            event.getAnswers()
                    .stream()
                    .filter(w ->
                            !eventDto.getAnswers().stream().mapToInt(e->e.getId())
                                    .boxed().collect(Collectors.toSet())
                                    .contains(w.getId()))
                    .collect(Collectors.toSet())
                    .forEach(event::removeAnswer);
            // Обновляем event данными из eventDto
            eventMapper.mapUpdateEventFromDto(eventDto, event);
        }
        // Задаем время изменения
        event.setUpdateTime(LocalDateTime.now());
        eventService.save(event);
        return event.getId();
    }
}
