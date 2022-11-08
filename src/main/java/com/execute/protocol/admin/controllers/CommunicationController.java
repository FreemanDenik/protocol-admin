package com.execute.protocol.admin.controllers;

import com.execute.protocol.admin.entities.Answer;
import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.mappers.AnswerMapper;
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

    @GetMapping("/")
    public String index(ModelMap model, Principal principal) {
        model.addAttribute("name", principal.getName());
        return "communication/index";
    }

    @GetMapping("/event")
    public String create(ModelMap model, @RequestParam(value = "id", defaultValue = "0") int eventId) {
        if (eventId > 0) {
            Event event = eventService.getEvent(eventId);
            EventDto eventDto = EventMapper.INSTANCE.mapEventToDto(event);
            model.addAttribute("model", eventDto);
        } else {
            EventDto emptyEventDto = new EventDto();
            List<AnswerDto> answersDto = new ArrayList<>(Arrays.asList(new AnswerDto()));
            emptyEventDto.setAnswers(answersDto);
            model.addAttribute("model", emptyEventDto);
        }
        return "communication/event";
    }

    @ResponseBody
    @PostMapping(value = "/event")
    public int create(@RequestBody EventDto eventDto) {

        Event event = EventMapper.INSTANCE.mapEventFromDto(eventDto);
        event.setUpdateTime(LocalDateTime.now());

        if (event.getId() == 0) {
            event.setCreateTime(LocalDateTime.now());
            eventService.save(event);

        } else {
            Event update = eventService.getEvent(event.getId());

            answerService.deleteAnswersInDtoDifferent(eventDto.getAnswers(), update.getAnswers());
            EventMapper.INSTANCE.updateEventFromDto(eventDto, update);

            eventService.save(update);
        }

        return event.getId();
    }
}
