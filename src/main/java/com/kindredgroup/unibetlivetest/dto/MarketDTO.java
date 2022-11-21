package com.kindredgroup.unibetlivetest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarketDTO implements Serializable {
    private static final long serialVersionUID = -7596489426124965893L;

    private Long id;
    private String name;
    private List<SelectionDTO> selections;

}
