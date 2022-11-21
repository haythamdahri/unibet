package com.kindredgroup.unibetlivetest.mappers;

import com.kindredgroup.unibetlivetest.dto.SelectionDTO;
import com.kindredgroup.unibetlivetest.entity.Selection;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {BetMapper.class})
public interface SelectionMapper extends GenericMapper<Selection, SelectionDTO> {
}
