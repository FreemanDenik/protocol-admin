package com.execute.protocol.admin.mappers;


import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.dto.EventDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring"/*, uses = CategoryTranslator.class*/)
public interface EventMapper {
    EventMapper INSTANCE = Mappers.getMapper(EventMapper.class);
    EventDto mapEventToDto(Event event);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "answers", target = "answers")
    Event mapEventFromDto(EventDto eventDto);

    @Mapping(target = "createTime", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEventFromDto(EventDto eventDto, @MappingTarget Event event);
}
