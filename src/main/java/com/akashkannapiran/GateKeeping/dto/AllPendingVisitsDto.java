package com.akashkannapiran.GateKeeping.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AllPendingVisitsDto {

    private List<VisitDto> visits;

    private Long totalRows;

    private Integer totalPages;
}
