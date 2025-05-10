package com.akashkannapiran.GateKeeping.util;

import com.akashkannapiran.GateKeeping.dto.AddressDto;
import com.akashkannapiran.GateKeeping.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class CommonUtil {

    public Address convertAddressDto(AddressDto addressDto) {
        Address address = Address.builder()
                            .line1(addressDto.getLine1())
                            .line2(addressDto.getLine2())
                            .city(addressDto.getCity())
                            .pincode(addressDto.getPincode())
                            .build();

        return address;
    }
}
