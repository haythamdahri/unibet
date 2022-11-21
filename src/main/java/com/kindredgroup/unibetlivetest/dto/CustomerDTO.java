package com.kindredgroup.unibetlivetest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO implements Serializable {
    private static final long serialVersionUID = 1000193704848317592L;

    private Long id;
    private String pseudo;
    private BigDecimal balance;
    private List<BetDTO> bets;

}
