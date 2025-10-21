package com.equipe_sc.sistemas_corporativos.candidate;

import com.equipe_sc.sistemas_corporativos.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "candidates")
public class Candidate {

    //region Attributes

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @MapsId
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", nullable = false)
    private User user;

    //endregion

}
