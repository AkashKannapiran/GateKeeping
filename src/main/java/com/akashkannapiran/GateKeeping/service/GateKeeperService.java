package com.akashkannapiran.GateKeeping.service;

import com.akashkannapiran.GateKeeping.dto.AddressDto;
import com.akashkannapiran.GateKeeping.dto.VisitDto;
import com.akashkannapiran.GateKeeping.dto.VisitorDto;
import com.akashkannapiran.GateKeeping.ennums.VisitStatus;
import com.akashkannapiran.GateKeeping.entity.Address;
import com.akashkannapiran.GateKeeping.entity.Flat;
import com.akashkannapiran.GateKeeping.entity.Visit;
import com.akashkannapiran.GateKeeping.entity.Visitor;
import com.akashkannapiran.GateKeeping.exception.BadRequestException;
import com.akashkannapiran.GateKeeping.exception.NotFoundException;
import com.akashkannapiran.GateKeeping.repo.FlatRepo;
import com.akashkannapiran.GateKeeping.repo.VisitRepo;
import com.akashkannapiran.GateKeeping.repo.VisitorRepo;
import com.akashkannapiran.GateKeeping.util.CommonUtil;
import org.springframework.transaction.annotation.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class GateKeeperService {

    @Autowired
    private CommonUtil commonUtil;

    @Autowired
    private VisitorRepo visitorRepo;

    @Autowired
    private FlatRepo flatRepo;

    @Autowired
    private VisitRepo visitRepo;

    public VisitorDto getVisitorByIdNumber(String number) {
        Visitor visitor = visitorRepo.findByIdNumber(number);
        VisitorDto visitorDto = null;

        if (visitor != null) {
            visitorDto = visitorDto.builder()
                    .name(visitor.getName())
                    .email(visitor.getEmail())
                    .phone(visitor.getPhone())
                    .idNumber(visitor.getIdNumber())
                    .build();
        }
        return visitorDto;
    }

    public Long createVisitor(@Valid VisitorDto visitorDto) {
        Address address = commonUtil.convertAddressDto(visitorDto.getAddressDto());
        Visitor visitor = Visitor.builder()
                .name(visitorDto.getName())
                .email(visitorDto.getEmail())
                .phone(visitorDto.getPhone())
                .idNumber(visitorDto.getIdNumber())
                .address(address)
                .build();

        visitor = visitorRepo.save(visitor);
        return visitor.getId();
    }

    public Long createVisit(@Valid VisitDto visitDto) {
        Flat flat = flatRepo.findByNumber(visitDto.getFlatNumber());
        Visitor visitor = visitorRepo.findByIdNumber(visitDto.getIdNumber());
        Visit visit = Visit.builder()
                .status(VisitStatus.WAITING)
                .purpose(visitDto.getPurpose())
                .imgUrl(visitDto.getImgUrl())
                .noOfPeople(visitDto.getNoOfPeople())
                .visitor(visitor)
                .flat(flat)
                .build();

        visit = visitRepo.save(visit);
        return visit.getId();
    }

    @Transactional
    public String markEntry(Long id) {
        Optional<Visit> visitOptional = visitRepo.findById(id);

        if (visitOptional.isEmpty()) {
            throw new NotFoundException("Visit not found");
        }

        Visit visit = visitOptional.get();
        if (visit.getStatus().equals(VisitStatus.APPROVED)) {
            visit.setInTime(new Date());
        } else {
            throw new BadRequestException("Invalid state transition");
        }

        return "Done! Entry Time: " + new Date();
    }

    @Transactional
    public String markExit(Long id) {
        Optional<Visit> visitOptional = visitRepo.findById(id);

        if (visitOptional.isEmpty()) {
            throw new NotFoundException("Visit not found");
        }

        Visit visit = visitOptional.get();
        if (visit.getStatus().equals(VisitStatus.APPROVED) && visit.getInTime() != null) {
            visit.setOutTime(new Date());
            visit.setStatus(VisitStatus.COMPLETED);
        } else {
            throw new BadRequestException("Invalid state transition");
        }

        return "Done! Exit Time: " + new Date();
    }
}
