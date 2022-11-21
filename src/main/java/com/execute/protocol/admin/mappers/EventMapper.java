package com.execute.protocol.admin.mappers;


import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.dto.EventDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Преобразователь класс Event<br>
 * Для выполнения таких преобразований как mapEventToDto,<br>
 * необходимо данный интерфейс использовать через внедрение зависимости<br>
 * т.е. в процессе работы подтягиваются данные из БД
 */
@Mapper(componentModel = "spring",
        uses = EventTranslator.class)
public interface EventMapper {
    //EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);

    /**
     * Преобразуем Event в EventDto<br>
     * Данное преобразование использовать только объявив<br> {@link EventMapper} через внедрение зависимости
     * @param event {@link Event}
     * @return {@link EventDto}
     */

    EventDto mapEventToDto(Event event);
    /**
     * Преобразуем EventDto в Event
     * @param eventDto {@link EventDto}
     * @return {@link Event}
     */


    Event mapEventFromDto(EventDto eventDto);

    /**
     * Обновляет Event данными их EventDto
     * @param eventDto {@link EventDto}
     * @param event {@link Event}
     */
    @Mapping(target = "child", ignore = true)
    @Mapping(target = "mainParent", ignore = true)
    @Mapping(target = "ownParent", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void mapUpdateEventFromDto(EventDto eventDto, @MappingTarget Event event);
}
