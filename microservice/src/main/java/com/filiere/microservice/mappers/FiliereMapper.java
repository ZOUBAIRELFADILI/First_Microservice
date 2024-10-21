package com.filiere.microservice.mappers;

import com.filiere.microservice.dto.FiliereDTO;
import com.filiere.microservice.entity.Filiere;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class FiliereMapper {
    public FiliereDTO toDTO(Filiere filiere){
        return new FiliereDTO(
        filiere.getId(),
        filiere.getTitre(),
        filiere.getCycle()
        );
    }
    public Filiere fromDTO(FiliereDTO filiereDTO){
        Filiere filiere = new Filiere();
        BeanUtils.copyProperties(filiereDTO,filiere);
        return filiere;
    }
}
