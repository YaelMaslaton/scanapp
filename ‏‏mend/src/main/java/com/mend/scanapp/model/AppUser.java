package com.mend.scanapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "App_User")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserID")
    private Long userId;

    @Column(name = "Username", length = 50)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(name = "UserType", length = 50)
    private UserType userType;

    @Column(name = "Email", length = 100)
    private String email;

    @OneToMany(mappedBy = "appUser")
    private List<Organization> organizations;

    @OneToMany(mappedBy = "appUser")
    private List<Scan> scans;

    @Override
    public String toString() {
        return "AppUser{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
