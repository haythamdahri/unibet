package com.kindredgroup.unibetlivetest.mappers;

import com.kindredgroup.unibetlivetest.dto.BetDTO;
import com.kindredgroup.unibetlivetest.entity.Bet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BetMapper extends GenericMapper<Bet, BetDTO> {
}
