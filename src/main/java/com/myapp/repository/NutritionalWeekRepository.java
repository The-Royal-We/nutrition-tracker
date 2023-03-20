package com.myapp.repository;

import com.myapp.domain.NutritionalWeek;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NutritionalWeek entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NutritionalWeekRepository extends JpaRepository<NutritionalWeek, Long> {}
