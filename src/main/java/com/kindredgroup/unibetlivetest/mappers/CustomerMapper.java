package com.kindredgroup.unibetlivetest.mappers;

import com.kindredgroup.unibetlivetest.dto.CustomerDTO;
import com.kindredgroup.unibetlivetest.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends GenericMapper<Customer, CustomerDTO> {
}
