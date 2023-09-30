package com.vannguyen.jobseeker.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.engine.spi.CascadeStyle;

import java.util.Date;

@Entity
@Table(name = "jobs")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String description;

    private String requiredSkill;

    private String address;
    private double salary;

    @Enumerated(EnumType.STRING)
    private JobTypes jobTypes;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updateAt;

    private Date endedAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Company company;
}
