package com.execute.protocol.admin.mappers;


import com.execute.protocol.admin.entities.Answer;
import com.execute.protocol.admin.entities.Event;
import com.execute.protocol.dto.AnswerDto;
import com.execute.protocol.dto.EventDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);
//
//    @Mapping(target = "event", ignore = true)
//    @Mapping(target = "id", ignore = true)
    //List<Answer> mapAnswerFromDto(List<AnswerDto> eventDto);
    @Mapping(target = "event", ignore = true)
   // @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAnswerFromDto(List<AnswerDto> answerDto, @MappingTarget List<Answer> answer);

}
