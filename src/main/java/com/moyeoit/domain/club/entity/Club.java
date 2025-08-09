package com.moyeoit.domain.club.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String position;
    private String slogan;
    private String bio;
    private Integer establishment;
    private Integer total_participant;
    private Integer operation;
    private String offline;
    private String online;
    private String location;
    private String address;
    private boolean recruiting;
    private String imageUrl;


    @OneToOne(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private ClubRecruitment recruitment;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubActivity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubSchedule> schedules = new ArrayList<>();

    //연관관계 편의 메소드
    public void setRecruitment(ClubRecruitment recruitment) {
        this.recruitment = recruitment;
    }
}
