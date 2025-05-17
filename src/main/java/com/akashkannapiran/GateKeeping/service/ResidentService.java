package com.akashkannapiran.GateKeeping.service;

import com.akashkannapiran.GateKeeping.dto.AllPendingVisitsDto;
import com.akashkannapiran.GateKeeping.dto.VisitDto;
import com.akashkannapiran.GateKeeping.ennums.VisitStatus;
import com.akashkannapiran.GateKeeping.entity.Flat;
import com.akashkannapiran.GateKeeping.entity.User;
import com.akashkannapiran.GateKeeping.entity.Visit;
import com.akashkannapiran.GateKeeping.entity.Visitor;
import com.akashkannapiran.GateKeeping.exception.BadRequestException;
import com.akashkannapiran.GateKeeping.exception.NotFoundException;
import com.akashkannapiran.GateKeeping.repo.FlatRepo;
import com.akashkannapiran.GateKeeping.repo.UserRepo;
import com.akashkannapiran.GateKeeping.repo.VisitRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResidentService {

    @Autowired
    private VisitRepo visitRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private FlatRepo flatRepo;


    public String updateVisit(Long id, VisitStatus visitStatus) {
        if (visitStatus != VisitStatus.REJECTED && visitStatus != VisitStatus.APPROVED) {
            throw new BadRequestException("Invalid state transition");
        }

        Visit visit = visitRepo.findById(id).get();

        if (visit == null) {
            throw new NotFoundException("Visit not found");
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (user.getFlat() != visit.getFlat()) {
            throw new BadRequestException("Wrong visit id");
        }

        if (VisitStatus.WAITING.equals(visit.getStatus())) {
            visit.setStatus(visitStatus);
            visit.setApprovedBy(user);
            visitRepo.save(visit);
        } else {
            throw new BadRequestException("Invalid state transition");
        }

        return "Done";
    }

    public List<VisitDto>  getPendingVisits(Long userId) {
        User user = userRepo.findById(userId).get();
        Flat flat = user.getFlat();

        List<Visit> visitList = visitRepo.findByStatusAndFlat(VisitStatus.WAITING, flat);
        List<VisitDto> visitDtoList = new ArrayList<>();

        for (Visit visit : visitList) {
            Visitor visitor = visit.getVisitor();

            VisitDto visitDto = VisitDto.builder()
                    .flatNumber(flat.getNumber())
                    .purpose(visit.getPurpose())
                    .noOfPeople(visit.getNoOfPeople())
                    .imgUrl(visit.getImgUrl())
                    .visitorName(visitor.getName())
                    .visitorPhone(visitor.getPhone())
                    .visitStatus(visit.getStatus())
                    .idNumber(visitor.getIdNumber())
                    .build();

            visitDtoList.add(visitDto);
        }

        return visitDtoList;
    }

    public AllPendingVisitsDto getPendingVisitByPage(Long userId, Integer pageNo, Integer pageSize) {
        AllPendingVisitsDto allPendingVisitsDto = new AllPendingVisitsDto();
        List<VisitDto> visitDtoList = new ArrayList<>();
        User user = userRepo.findById(userId).get();
        Flat flat = user.getFlat();

        Pageable pageable = Pageable.ofSize(pageSize).withPage(pageNo);

        Page<Visit> visitPage = visitRepo.findByStatusAndFlat(VisitStatus.WAITING, flat, pageable);
        List<Visit> visitList = visitPage.stream().toList();

        for (Visit visit : visitList) {
            Visitor visitor = visit.getVisitor();
            VisitDto visitDto = VisitDto.builder()
                    .flatNumber(flat.getNumber())
                    .purpose(visit.getPurpose())
                    .noOfPeople(visit.getNoOfPeople())
                    .imgUrl(visit.getImgUrl())
                    .visitorName(visitor.getName())
                    .visitorPhone(visitor.getPhone())
                    .visitStatus(visit.getStatus())
                    .idNumber(visitor.getIdNumber())
                    .build();

            visitDtoList.add(visitDto);
        }

        allPendingVisitsDto.setVisits(visitDtoList);
        allPendingVisitsDto.setTotalPages(visitPage.getTotalPages());
        allPendingVisitsDto.setTotalRows(visitPage.getTotalElements());

        return allPendingVisitsDto;
    }
}
