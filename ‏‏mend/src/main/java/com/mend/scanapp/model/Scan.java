    package com.mend.scanapp.model;
    import jakarta.persistence.*;
    import lombok.Data;

    @Data
    @Entity
    @Table(name = "Scan")
    public class Scan {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "ScanID")
        private Long scanId;

        @Enumerated(EnumType.STRING)
        private ScanType type;

        @Enumerated(EnumType.STRING)
        private ScanStatus status;

        @ManyToOne
        @JoinColumn(name = "CommitID")
        private Commit commit;

        @Column(name = "Issues")
        private int issues;

        @Column(name = "IsValid")
        private boolean valid;

        @ManyToOne
        @JoinColumn(name = "appUser")
        private AppUser appUser;
    }