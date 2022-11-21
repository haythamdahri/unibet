package com.kindredgroup.unibetlivetest.mappers;

import com.kindredgroup.unibetlivetest.dto.MarketDTO;
import com.kindredgroup.unibetlivetest.entity.Market;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MarketMapper extends GenericMapper<Market, MarketDTO> {
}
