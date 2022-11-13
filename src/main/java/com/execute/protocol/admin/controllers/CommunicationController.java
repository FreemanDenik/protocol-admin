package com.execute.protocol.admin.controllers;

import com.execute.protocol.admin.entities.Category;
import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.admin.entities.QCategory;
import com.execute.protocol.admin.mappers.CategoryMapper;
import com.execute.protocol.admin.mappers.EventMapper;
import com.execute.protocol.admin.repositories.CategoryRepository;
import com.execute.protocol.admin.services.AnswerService;
import com.execute.protocol.admin.services.CategoryService;
import com.execute.protocol.admin.services.EventService;
import com.execute.protocol.dto.AnswerDto;
import com.execute.protocol.dto.CategoryDto;
import com.execute.protocol.dto.CategoryFinerDto;
import com.execute.protocol.dto.EventDto;
import com.querydsl.core.BooleanBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.*;

@Controller
@RequestMapping(value = "communication")
public class CommunicationController {
    private final CategoryService categoryService;
    private final CategoryRepository repository;
    private final EventService eventService;
    private final AnswerService answerService;

    private final EventMapper eventMapper;
    public CommunicationController(CategoryService categoryService, CategoryRepository repository, EventService eventService, AnswerService answerService, EventMapper eventMapper) {
        this.categoryService = categoryService;
        this.repository = repository;
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
        model.addAttribute("specials", new TreeMap<>(Map.of(
                1, "Телохранитель",
                2, "Мешок золота"
        )));
        return "communication/event";
    }
    /**
     * Контроллер получения категории по части названия
     * @param fastFinerDto - краткие сведения по быстрому поиску и исключению
     * @return List CategoryDto
     */
    @ResponseBody
    @PostMapping(value = "/category")
    public Set<CategoryDto> category(@RequestBody CategoryFinerDto fastFinerDto) {
        int page = 0;
        int pageSize = 10;
        QCategory qCategory = QCategory.category;

        BooleanBuilder predicates = new BooleanBuilder();

        Optional.ofNullable(fastFinerDto.getSearch())
                .map(qCategory.title::contains).map(predicates::and);

        Optional.ofNullable(fastFinerDto.getExcludes())
                .map(qCategory.id::in).map(predicates::andNot);

        Page<Category> pageCategories = categoryService.getCategoriesBySearchAndWithExcludes(predicates, PageRequest.of(page, pageSize));
        // Преобразуем список категории в Dto список
        return CategoryMapper.INSTANCE.mapSetCategoryToSetDto(pageCategories.toSet());
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
            // метод deleteAnswersInDtoDifferent использовать до Mapper преобразовании
            answerService.deleteAnswersInDtoDifferent(eventDto.getAnswers(), event.getAnswers());
            // Обновляем event данными из eventDto
            eventMapper.mapUpdateEventFromDto(eventDto, event);
        }
        // Задаем время изменения
        event.setUpdateTime(LocalDateTime.now());
        eventService.save(event);
        return event.getId();
    }
}
