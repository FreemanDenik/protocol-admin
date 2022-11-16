package com.execute.protocol.admin.mappers;


import com.execute.protocol.admin.entities.Thing;
import com.execute.protocol.admin.interfaces.FastFiner;
import com.execute.protocol.dto.CategoryDto;
import com.execute.protocol.dto.ThingDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Set;

/**
 * Преобразователь класс Category
 */
@Mapper
public interface ThingMapper {
    ThingMapper INSTANCE = Mappers.getMapper(ThingMapper.class);
    /**
     * Преобразуем коллекцию Set {@link Thing} в коллекцию Set {@link ThingDto}
     * @param thingSet Set Thing
     * @return Set ThingDto
     */
    Set<ThingDto> mapSetThingToSetThingDto(Set<Thing> thingSet);
    /**
     * Преобразуем коллекцию Set {@link ThingDto} в коллекцию Set {@link Thing}
     * @param thing Set Thing
     * @return Set ThingDto
     */
    ThingDto mapThingToThingDto(Thing thing);
//    /**
//     * Преобразуем коллекцию Set {@link FastFiner} в коллекцию Set {@link ThingDto}
//     * @param fastFiner Set FastFiner
//     * @return Set ThingDto
//     */
//    Set<ThingDto> mapSetFastFinerToSetThingDto(Set<? extends FastFiner> fastFiner);

}
