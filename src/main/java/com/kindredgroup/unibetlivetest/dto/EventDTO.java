package com.kindredgroup.unibetlivetest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO implements Serializable {
    private static final long serialVersionUID = -5945204892664733121L;

    private Long id;
    private String name;
    private Date startDate;
    private List<MarketDTO> markets;

}
