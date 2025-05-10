package com.akashkannapiran.GateKeeping.service;


import com.akashkannapiran.GateKeeping.dto.AddressDto;
import com.akashkannapiran.GateKeeping.dto.UserDto;
import com.akashkannapiran.GateKeeping.ennums.UserStatus;
import com.akashkannapiran.GateKeeping.entity.Address;
import com.akashkannapiran.GateKeeping.entity.Flat;
import com.akashkannapiran.GateKeeping.entity.User;
import com.akashkannapiran.GateKeeping.repo.FlatRepo;
import com.akashkannapiran.GateKeeping.repo.UserRepo;
import com.akashkannapiran.GateKeeping.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FlatRepo flatRepo;

    @Autowired
    private CommonUtil commonUtil;

    public Long createUser(UserDto userDto) {
        AddressDto addressDto = userDto.getAddress();
        Address address = commonUtil.convertAddressDto(addressDto);
        Flat flat = null;

        if (userDto.getFlatNo() != null) {
            flat = flatRepo.findByNumber(userDto.getFlatNo());
        }

        User user = User.builder()
                .name(userDto.getName())
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .idNumber(userDto.getIdNumber())
                .role(userDto.getRole())
                .flat(flat)
                .address(address)
                .status(UserStatus.ACTIVE)
                .build();

        user = userRepo.save(user);

        return user.getId();
    }
}
