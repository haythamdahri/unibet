package com.kindredgroup.unibetlivetest.dto;

import com.kindredgroup.unibetlivetest.types.BetState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetDTO implements Serializable {
    private static final long serialVersionUID = -4624190991320880240L;

    private Long id;
    private int name;
    private Date date;
    private SelectionDTO selection;
    private BetState betState;
    private CustomerDTO customer;

}
