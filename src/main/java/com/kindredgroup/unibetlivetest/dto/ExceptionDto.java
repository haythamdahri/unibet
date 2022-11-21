package com.kindredgroup.unibetlivetest.dto;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@Builder(toBuilder = true)
public class ExceptionDto {

    private String path;
    private String errormessage;
    private String timestamp;
}
