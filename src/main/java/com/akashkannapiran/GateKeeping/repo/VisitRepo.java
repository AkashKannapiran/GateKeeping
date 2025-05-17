package com.akashkannapiran.GateKeeping.repo;

import com.akashkannapiran.GateKeeping.ennums.VisitStatus;
import com.akashkannapiran.GateKeeping.entity.Flat;
import com.akashkannapiran.GateKeeping.entity.Visit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VisitRepo extends JpaRepository<Visit, Long> {

    List<Visit> findByStatusAndFlat(VisitStatus visitStatus, Flat flat);

    Page<Visit> findByStatusAndFlat(VisitStatus visitStatus, Flat flat, Pageable pageable);

    List<Visit> findByStatusAndCreateDateLessThanEqual(VisitStatus visitStatus, Date date);
}
