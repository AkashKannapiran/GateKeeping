package com.akashkannapiran.GateKeeping.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitorDto {

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String email;

    @NotNull
    @Size(min = 10)
    private String phone;

    @NotNull
    @Size(max = 255)
    private String idNumber;

    private AddressDto addressDto;
}
