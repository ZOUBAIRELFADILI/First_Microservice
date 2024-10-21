package com.filiere.microservice.service;

import com.filiere.microservice.dto.FiliereDTO;
import com.filiere.microservice.entity.Filiere;

import java.util.List;

public interface FiliereService {
    FiliereDTO getFiliere(Long id);
    List<FiliereDTO> getAllFiliere();
    Filiere addFiliere(FiliereDTO filiereDTO);
    Filiere updateFiliere(Long id, FiliereDTO filiereDTO);
    void deleteFiliere(Long id);
}

