package com.filiere.microservice.service;

import com.filiere.microservice.Repository.FiliereRepository;
import com.filiere.microservice.dto.FiliereDTO;
import com.filiere.microservice.entity.Filiere;
import com.filiere.microservice.mappers.FiliereMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FiliereServiceImple implements FiliereService {

    final FiliereRepository filiereRepository;
    final FiliereMapper filiereMapper;


    public FiliereServiceImple(FiliereRepository filiereRepository, FiliereMapper filiereMapper) {
        this.filiereRepository = filiereRepository;
        this.filiereMapper = filiereMapper;
    }

    @Override
    public FiliereDTO getFiliere(Long id) {
        Optional<Filiere> filiereOptional = filiereRepository.findById(id);
        if (filiereOptional.isPresent()) {
            Filiere filiere = filiereOptional.get();
            return filiereMapper.toDTO(filiere);
        } else {
            throw new EntityNotFoundException("Filiere not found");
        }
    }

    @Override
    public List<FiliereDTO> getAllFiliere() {
        List<FiliereDTO> filiereDTOList = new ArrayList<>();
        List<Filiere> filieres = filiereRepository.findAll();
        for (Filiere f : filieres) {
            filiereDTOList.add(filiereMapper.toDTO(f));
        }
        return filiereDTOList;
    }

    @Override
    public Filiere addFiliere(FiliereDTO filiereDTO) {
        Filiere filiere = filiereMapper.fromDTO(filiereDTO);
        filiereRepository.save(filiere);
        return filiere;
    }

    @Override
    public Filiere updateFiliere (Long id, FiliereDTO filiereDTO) {
        Optional<Filiere> optionalFiliere = filiereRepository.findById(id);

        if (optionalFiliere.isPresent()) {
            Filiere existingFiliere = optionalFiliere.get();

            // Manually map the properties that need to be updated
            existingFiliere.setTitre(filiereDTO.getTitre());
            existingFiliere.setCycle(filiereDTO.getCycle());
            // You can add more fields here if needed, excluding those that shouldn't be changed

            // Save the updated entity
            filiereRepository.save(existingFiliere);

            return existingFiliere;
        } else {
            throw new EntityNotFoundException("No Product with id =" + id);
        }
    }

    @Override
    public void deleteFiliere(Long id) {
        if (filiereRepository.existsById(id)) {
            filiereRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Filiere not found");
        }
    }
}
