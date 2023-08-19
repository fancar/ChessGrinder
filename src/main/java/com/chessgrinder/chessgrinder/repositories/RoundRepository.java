package com.chessgrinder.chessgrinder.repositories;

import com.chessgrinder.chessgrinder.entities.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.rest.core.annotation.*;

@RepositoryRestResource
public interface RoundRepository  extends JpaRepository<Round, Integer> {
}
