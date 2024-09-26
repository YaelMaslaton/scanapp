package com.mend.scanapp.model;


import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Repository")
public class Repository {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long repositoryId;

    @Column(name = "RepositoryName", length = 100)
    private String repositoryName;

    @Column(name = "Description", length = 200)
    private String description;

    @ManyToOne
    @JoinColumn(name = "OrganizationID")
    private Organization organization;

    @OneToMany(mappedBy = "repository")
    private List<Branch> branches;
}


