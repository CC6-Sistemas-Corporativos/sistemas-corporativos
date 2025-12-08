package com.cc6.cadidate.classes;


import com.cc6.cadidate.dtos.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidates")
@SQLDelete(sql = "UPDATE candidates SET deleted_at = NOW() WHERE id = ?")
public class Candidate {

    @Id
    private UUID id;

    @Column(name = "curriculum_url", nullable = false)
    private String curriculumUrl;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Transient // Não será persistido no banco de candidates
    private User user;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}
