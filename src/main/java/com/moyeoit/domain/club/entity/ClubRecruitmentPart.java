package com.moyeoit.domain.club.entity;

import com.moyeoit.domain.user.domain.Job;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_club_recruitment_part")
public class ClubRecruitmentPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_recruitment_id") // 외래 키(FK) 컬럼명 지정
    private ClubRecruitment clubRecruitment;

    @Column(name = "job_id")
    private Long jobId;

}
