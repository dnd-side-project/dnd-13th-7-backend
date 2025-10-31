package com.moyeoit.domain.club.entity.process;

import com.moyeoit.domain.club.entity.Club;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "tb_club_process")
public class ClubProcess {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_process_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "description")
    private String description;

    @Column(name = "sequence")
    private Integer sequence;

}
