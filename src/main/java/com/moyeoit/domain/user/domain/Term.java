package com.moyeoit.domain.user.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Table(name = "tb_term")
public class Term {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "app_user_id")
    private User user;

    private boolean overAge;
    private boolean termOfService;
    private boolean privacyPolicy;
    private boolean marketingPrivacy;
    private boolean eventNotification;

}