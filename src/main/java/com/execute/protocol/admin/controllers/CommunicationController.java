package com.execute.protocol.admin.controllers;

import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.mappers.EventMapper;
import com.execute.protocol.admin.services.AnswerService;
import com.execute.protocol.admin.services.CategoryService;
import com.execute.protocol.admin.services.EventService;
import com.execute.protocol.dto.AnswerDto;
import com.execute.protocol.dto.EventDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping("communication")
public class CommunicationController {
    private final CategoryService categoryService;
    private final EventService eventService;
    private final AnswerService answerService;

    public CommunicationController(CategoryService categoryService, EventService eventService, AnswerService answerService) {
        this.categoryService = categoryService;
        this.eventService = eventService;
        this.answerService = answerService;
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
     * @param model
     * @param eventId
     * @return View
     */
    @GetMapping("/event")
    public String create(ModelMap model, @RequestParam(value = "id", defaultValue = "0") int eventId) {
        Event event;
        if (eventId > 0) {
            Event event1 = eventService.getEvent(eventId);
            EventDto eventDto = EventMapper.INSTANCE.mapEventToDto(event1);
            model.addAttribute("model", eventDto);
        } else {
            // Все эти пустые экземпляры создаются для того что бы
            // можно было использовать thymeleaf и проверять значения на NULL
            EventDto emptyEventDto = new EventDto();
            // Так же пустая модель AnswerDto создается чтобы одна запись всегда была
            List<AnswerDto> answersDto = new ArrayList<>(Arrays.asList(new AnswerDto()));
            emptyEventDto.setAnswers(answersDto);
            model.addAttribute("model", emptyEventDto);

        }

        model.addAttribute("categories", new TreeMap<>(Map.of(
                1, "Келен незаметно подбрасывает записку",
                2, "Стража врывается в таверну"
        )));

        model.addAttribute("specials", new TreeMap<>(Map.of(
                1, "Телохранитель",
                2, "Мешок золота"
        )));
        model.addAttribute("openCategories", new TreeMap<>(Map.of(
                1, "Келен незаметно подбрасывает записку",
                2, "Странник в углу таверны предлагает беседу"
        )));
        model.addAttribute("closeCategories", new TreeMap<>(Map.of(
                1, "Стража врывается в таверну",
                2, "Одна компания пъянных ребят шумит"
        )));

        return "communication/event";
    }

    /**
     * POST Создание/Изменение
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
            event = EventMapper.INSTANCE.mapEventFromDto(eventDto);
            event.setUpdateTime(LocalDateTime.now());
        // Изменение события
        } else {
            event = eventService.getEvent(eventId);
            // Удаление из Event.Answer записей которых нет в EventDto.AnswerDto
            // метод deleteAnswersInDtoDifferent использовать до Mapper преобразовании
            answerService.deleteAnswersInDtoDifferent(eventDto.getAnswers(), event.getAnswers());
            // Преобразовываем eventDto в event
            EventMapper.INSTANCE.updateEventFromDto(eventDto, event);
        }
        event.setCreateTime(LocalDateTime.now());
        eventService.save(event);
        return event.getId();
    }
}
