package com.microservice.etudiant.mappers;

import com.microservice.etudiant.dto.EtudiantDTO;
import com.microservice.etudiant.entity.Etudiant;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class EtudiantMapper {
    public EtudiantDTO toDTO(Etudiant etudiant){
        return new EtudiantDTO(
                etudiant.getId(),
                etudiant.getNom(),
                etudiant.getPrenom(),
                etudiant.getDateNaissance(),
                etudiant.getEmail(),
                etudiant.getTelephone(),
                etudiant.getIdFiliere()
        );
    }
    public Etudiant fromDTO(EtudiantDTO etusiantDTO){
        Etudiant etudiant = new Etudiant();
        BeanUtils.copyProperties(etusiantDTO,etudiant);
        return etudiant;
    }
}
