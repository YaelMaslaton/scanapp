package com.mend.scanapp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Commit")
public class Commit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommitID")
    private int commitId;

    @ManyToOne
    @JoinColumn(name = "BranchID")
    private Branch branch;

    @Column(name = "CommitMessage", columnDefinition = "TEXT")
    private String commitMessage;

    @Column(name = "CommitDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commitDate;
}
