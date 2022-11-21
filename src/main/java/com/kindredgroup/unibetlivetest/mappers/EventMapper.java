package com.kindredgroup.unibetlivetest.mappers;

import com.kindredgroup.unibetlivetest.dto.EventDTO;
import com.kindredgroup.unibetlivetest.entity.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MarketMapper.class})
public interface EventMapper extends GenericMapper<Event, EventDTO> {
}
