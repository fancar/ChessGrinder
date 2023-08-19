package com.chessgrinder.chessgrinder.repositories;

import com.chessgrinder.chessgrinder.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Integer> {
}
