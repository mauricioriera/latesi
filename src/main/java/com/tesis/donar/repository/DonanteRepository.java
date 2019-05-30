package com.tesis.donar.repository;

import com.tesis.donar.domain.Donante;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Donante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DonanteRepository extends JpaRepository<Donante, Long> {

}
