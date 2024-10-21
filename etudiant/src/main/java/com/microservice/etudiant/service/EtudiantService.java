package com.microservice.etudiant.service;

import com.microservice.etudiant.dto.EtudiantDTO;
import com.microservice.etudiant.entity.Etudiant;

import java.util.List;

public interface EtudiantService {
    EtudiantDTO getEtudiant(Long id);
    List<EtudiantDTO> getAllEtudiant();
    Etudiant addEtudiant(EtudiantDTO filiereDTO);
    Etudiant updateEtudiant(Long id, EtudiantDTO filiereDTO);
    void deleteEtudiant(Long id);
}
