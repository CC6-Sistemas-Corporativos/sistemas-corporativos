package com.cc6.job.classes.inscriptions;

import com.cc6.job.classes.enums.InscriptionStatus;
import com.cc6.job.classes.jobVacancys.JobVacancy;
import com.cc6.job.dtos.candidate.CandidateDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inscriptions")
public class Inscription {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "candidate_id", nullable = false)
    private UUID candidateId;

    @Transient
    private CandidateDto candidate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InscriptionStatus status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_vacancy_id", nullable = false)
    private JobVacancy jobVacancy;

    // Getter personalizado para o ID do job (evita inicializar proxy completo)
    public UUID getJobVacancyId() {
        if (jobVacancy == null) {
            return null;
        }
        // Usa apenas o ID sem inicializar o proxy completo
        return jobVacancy.getId();
    }

}

