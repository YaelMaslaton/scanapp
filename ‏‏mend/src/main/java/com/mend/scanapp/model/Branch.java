package com.mend.scanapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BranchID")
    private Long branchId;

    @Column(name = "BranchName", length = 50)
    private String branchName;

    @ManyToOne
    @JoinColumn(name = "RepositoryID")
    private Repository repository;

    @OneToMany(mappedBy = "branch")
    private List<Commit> commits;
}
