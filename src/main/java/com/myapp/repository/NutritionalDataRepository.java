package com.myapp.repository;

import com.myapp.domain.NutritionalData;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the NutritionalData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NutritionalDataRepository extends JpaRepository<NutritionalData, Long> {}
