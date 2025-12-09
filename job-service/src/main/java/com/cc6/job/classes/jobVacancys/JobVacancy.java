package com.cc6.job.classes.jobVacancys;

import com.cc6.job.classes.inscriptions.Inscription;
import com.cc6.job.dtos.recruiter.RecruiterDto;
import jakarta.persistence.*;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jobs_vacancy")
public class JobVacancy {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "recruiter_id", nullable = false)
    private UUID recruiterId;

    @Transient
    private RecruiterDto recruiter;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "description", nullable = true)
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "jobVacancy", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Inscription> inscriptions = new HashSet<>();

    public void setTitle(String title) {
        if (title == null){
            throw new IllegalArgumentException("[JobVacancy] Title cannot be null");
        }
        this.title = title.toUpperCase();
    }

    public void setDescription(String description) {
        if (description != null){
            this.description = description.toUpperCase();
        }
    }
}

