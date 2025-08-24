package com.moyeoit.domain.club.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "slogan")
    private String slogan;

    @Column(name = "bio")
    private String bio;

    @Column(name = "establishment")
    private Integer establishment;

    @Column(name = "total_participant")
    private Integer totalParticipant;

    @Column(name = "operation")
    private Integer operation;

    @Column(name = "offline")
    private String offline;

    @Column(name = "online")
    private String online;

    @Column(name = "location")
    private String location;

    @Column(name = "address")
    private String address;

    @Column(name = "recruiting")
    private Boolean recruiting;

    @Column(name = "image_url")
    private String imageUrl;


    //    @Setter // 연관관계 편의 메소드
    @OneToOne(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private ClubRecruitment recruitment;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubActivity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClubSchedule> schedules = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Process> process = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Position> position = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Target> target = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Club_ClubKeyword> clubKeywords = new ArrayList<>();

}
