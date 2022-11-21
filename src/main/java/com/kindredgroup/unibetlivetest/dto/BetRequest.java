package com.kindredgroup.unibetlivetest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BetRequest implements Serializable {
    private static final long serialVersionUID = -8119684621896676955L;

    @JsonProperty("selectionId")
    @NotNull(message = "selectionId cannot be null")
    private Long selectionId;

    @JsonProperty("cote")
    @NotNull(message = "cote cannot be null")
    private BigDecimal cote;

    @JsonProperty("mise")
    @NotNull(message = "mise cannot be null")
    private BigDecimal mise;

}
