package com.kindredgroup.unibetlivetest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kindredgroup.unibetlivetest.types.SelectionState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelectionDTO implements Serializable {
    private static final long serialVersionUID = 183392996864527411L;

    private Long id;
    private String name;
    private BigDecimal currentOdd;
    private SelectionState state;
    private List<BetDTO> bets;

}
