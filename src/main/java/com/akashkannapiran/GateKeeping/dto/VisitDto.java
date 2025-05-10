package com.akashkannapiran.GateKeeping.dto;

import com.akashkannapiran.GateKeeping.ennums.VisitStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VisitDto {

    private VisitStatus visitStatus;

    @NotNull
    @Size(max = 255)
    private String purpose;

    private Date inTime;

    private Date outTime;

    @Size(max = 255)
    private String imgUrl;

    @NotNull
    private Integer noOfPeople;

    @NotNull
    private String idNumber;

    @NotNull
    private String flatNumber;

    private String visitorName;

    private String visitorNumber;
}
