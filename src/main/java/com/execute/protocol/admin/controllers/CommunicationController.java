package com.execute.protocol.admin.controllers;

import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.mappers.CategoryMapper;
import com.execute.protocol.admin.mappers.EventMapper;
import com.execute.protocol.admin.services.AnswerService;
import com.execute.protocol.admin.services.CategoryService;
import com.execute.protocol.admin.services.EventService;
import com.execute.protocol.dto.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@Controller
@RequestMapping(value = "communication")
public class CommunicationController {
    private final EventService eventService;
    private final CategoryService categoryService;
    private final AnswerService answerService;
    private final EventMapper eventMapper;

    public CommunicationController(EventService eventService, CategoryService categoryService, AnswerService answerService, EventMapper eventMapper) {

        this.eventService = eventService;
        this.categoryService = categoryService;
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
    public String create(ModelMap model,
                         @RequestParam(value = "eventId", defaultValue = "0") int eventId,
                         @RequestParam(value = "categoryId", defaultValue = "0") int categoryId) {
        EventDto eventDto;
        if (eventId > 0) {
            Event event = eventService.getEvent(eventId);

            eventDto = eventMapper.mapEventToDto(event);
        } else {
            // Все эти пустые экземпляры создаются для того что бы
            // можно было использовать thymeleaf и проверять значения на NULL
            eventDto = new EventDto();
            if (categoryId > 0) {
                var category = categoryService.getCategory(categoryId);
                var categoryDto = CategoryMapper.INSTANCE.mapCategoryToCategoryDto(category);
                eventDto.setCategory(categoryDto);
            }
            // Так же пустая модель AnswerDto создается чтобы одна запись всегда была
            Set<AnswerDto> answersDto = Set.of(new AnswerDto());
            eventDto.setAnswers(answersDto);
        }

        model.addAttribute("model", eventDto);
        return "communication/event";
    }

    /**
     * POST Создание/Изменение события
     *
     * @param eventDto
     * @return boolean
     */
    @ResponseBody
    @PostMapping(value = "/event")
    public int create(@RequestBody EventDto eventDto,
                      @RequestParam(name = "answerId", defaultValue = "0") int answerId) {
        int eventId = eventDto.getId();
        Event event;

        if (eventId == 0) {
            // Создание события
            event = eventService.saveFromDto(eventDto);
        } else {
            // Обновление события (если у него есть связанные сущности Event, обновляет их категории)
            event = eventService.updateFromDto(eventDto);
            if (answerId > 0) {
                // Создание связанной (сюжетной связкой parent child) сущности
                return eventService.saveAndCreateEventLink(event, answerId);
            }
        }

        return event.getId();
    }
}
