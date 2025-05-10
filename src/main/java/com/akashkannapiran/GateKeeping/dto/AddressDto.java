package com.akashkannapiran.GateKeeping.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddressDto {

    private String line1;

    private String line2;

    private String city;

    private String pincode;
}
