package com.chessgrinder.chessgrinder.entities;

import java.math.*;
import java.util.*;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "participants")
@Slf4j
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Participant {

    @Id
    @Column(name = "id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "score")
    private BigDecimal score;

    @Column(name = "buchholz")
    private BigDecimal buchholz;
}
