package com.mend.scanapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Organization")
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrganizationID")
    private Long organizationId;

    @Column(name = "OrganizationName", length = 50)
    private String organizationName;

    @ManyToOne
    @JoinColumn(name = "UserID")
    private AppUser appUser;

    @OneToMany(mappedBy = "organization")
    private List<Repository> repositories;
}

