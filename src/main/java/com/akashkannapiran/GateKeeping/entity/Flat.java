package com.akashkannapiran.GateKeeping.entity;

import com.akashkannapiran.GateKeeping.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Flat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String number;

    @OneToMany(mappedBy = "flat")
    private Set<User> userSet;

    @CreationTimestamp
    private Date createDate;

    @UpdateTimestamp
    private Date updateDate;
}
