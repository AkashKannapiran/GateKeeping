package com.akashkannapiran.GateKeeping.repo;

import com.akashkannapiran.GateKeeping.entity.Flat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlatRepo extends JpaRepository<Flat, Long> {

    Flat findByNumber(String number);
}
