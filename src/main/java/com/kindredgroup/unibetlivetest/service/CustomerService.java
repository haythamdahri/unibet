package com.kindredgroup.unibetlivetest.service;

import com.kindredgroup.unibetlivetest.dto.CustomerDTO;
import com.kindredgroup.unibetlivetest.entity.Customer;

public interface CustomerService {

    /**
     * Find a customer by a given pseudo
     *
     * @param pseudo given {@link Customer} pseudo
     * @return found {@link Customer}
     */
    Customer findCustomerByPseudo(final String pseudo);

    /**
     * Find a customer by a given id
     *
     * @param customerId given {@link CustomerDTO} id
     * @return found {@link CustomerDTO}
     */
    CustomerDTO findCustomerById(final Long customerId);

    /**
     * Find a customer by a given id
     *
     * @param customerId given {@link Customer} id
     * @return found {@link Customer}
     */
    Customer findCustomerEntityById(final Long customerId);

    /**
     * Save ginve {@link Customer}
     *
     * @param customer given {@link Customer}
     * @return saved {@link Customer}
     */
    Customer saveCustomer(final Customer customer);
}
