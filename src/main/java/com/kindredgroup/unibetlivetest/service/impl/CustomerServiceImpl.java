package com.kindredgroup.unibetlivetest.service.impl;

import com.kindredgroup.unibetlivetest.dto.CustomerDTO;
import com.kindredgroup.unibetlivetest.entity.Customer;
import com.kindredgroup.unibetlivetest.exception.CustomException;
import com.kindredgroup.unibetlivetest.mappers.CustomerMapper;
import com.kindredgroup.unibetlivetest.repository.CustomerRepository;
import com.kindredgroup.unibetlivetest.service.CustomerService;
import com.kindredgroup.unibetlivetest.types.ExceptionType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public Customer findCustomerByPseudo(final String pseudo) {
        log.info("Find customer by pseudo {}", pseudo);
        return customerRepository.getCustomerByPseudo(pseudo)
                .orElseThrow(() -> CustomException.builder()
                        .message(String.format("customer %s not found", pseudo))
                        .exception(ExceptionType.CUSTOMER_NOT_FOUND)
                        .build()
                );
    }

    @Override
    public CustomerDTO findCustomerById(final Long customerId) {
        log.info("Find customer by id {}", customerId);
        return customerRepository.findById(customerId)
                .map(customerMapper::toDto)
                .orElseThrow(() -> CustomException.builder()
                        .message(String.format("customer with id %d not found", customerId))
                        .exception(ExceptionType.CUSTOMER_NOT_FOUND)
                        .build()
                );
    }

    @Override
    public Customer findCustomerEntityById(final Long customerId) {
        log.info("Find customer entity by id {}", customerId);
        return customerRepository.findById(customerId).orElse(null);
    }

    @Override
    public Customer saveCustomer(final Customer customer) {
        log.info("Save customer entity {}", customer);
        return customerRepository.save(customer);
    }

}
