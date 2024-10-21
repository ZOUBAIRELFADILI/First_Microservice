package com.filiere.microservice.Repository;

import com.filiere.microservice.entity.Filiere;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FiliereRepository extends JpaRepository<Filiere, Long> {
}
