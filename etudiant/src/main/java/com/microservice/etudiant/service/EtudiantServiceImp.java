package com.microservice.etudiant.service;

import com.microservice.etudiant.dto.EtudiantDTO;
import com.microservice.etudiant.entity.Etudiant;
import com.microservice.etudiant.mappers.EtudiantMapper;
import com.microservice.etudiant.repository.EtudiantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EtudiantServiceImp implements EtudiantService {
    final EtudiantRepository etudiantRepository;
    final EtudiantMapper etudiantMapper;
    final RestTemplate restTemplate;

    //@Value(staticConstructor = "${filiere.service.url}") // Externalize the Filiere service URL in application.properties
    private String filiereServiceUrl;

    public EtudiantServiceImp(EtudiantRepository etudiantRepository, EtudiantMapper etudiantMapper, RestTemplate restTemplate) {
        this.etudiantRepository = etudiantRepository;
        this.etudiantMapper = etudiantMapper;
        this.restTemplate = restTemplate;
    }


    @Override
    public EtudiantDTO getEtudiant(Long id) {
        Optional<Etudiant> etudiantOptional= etudiantRepository.findById(id);
        if (etudiantOptional.isPresent()) {
            Etudiant etudiant = etudiantOptional.get();
            return etudiantMapper.toDTO(etudiant);
        } else {
            throw new EntityNotFoundException("Filiere not found");
        }
    }

    @Override
    public List<EtudiantDTO> getAllEtudiant() {
        List<EtudiantDTO> filiereDTOList = new ArrayList<>();
        List<Etudiant> filieres = etudiantRepository.findAll();
        for (Etudiant e : filieres) {
            filiereDTOList.add(etudiantMapper.toDTO(e));
        }
        return filiereDTOList;
    }

    @Override
    public Etudiant addEtudiant(EtudiantDTO etudiantDTO) {
        Etudiant etudiant = etudiantMapper.fromDTO(etudiantDTO);
        etudiantRepository.save(etudiant);
        return etudiant;
    }

    @Override
    public Etudiant updateEtudiant (Long id, EtudiantDTO etudiantDTO) {
        Optional<Etudiant> optionalFiliere = etudiantRepository.findById(id);

        if (optionalFiliere.isPresent()) {
            Etudiant existingFiliere = optionalFiliere.get();

            // Manually map the properties that need to be updated
            existingFiliere.setNom(etudiantDTO.getNom());
            existingFiliere.setPrenom(etudiantDTO.getPrenom());
            existingFiliere.setDateNaissance(etudiantDTO.getDateNaissance());
            existingFiliere.setTelephone(etudiantDTO.getTelephone());
            existingFiliere.setEmail(etudiantDTO.getEmail());
            existingFiliere.setIdFiliere(etudiantDTO.getIdFiliere());
            // You can add more fields here if needed, excluding those that shouldn't be changed

            // Save the updated entity
            etudiantRepository.save(existingFiliere);

            return existingFiliere;
        } else {
            throw new EntityNotFoundException("No Etudiant with id =" + id);
        }
    }
    @Override
    public void deleteEtudiant(Long id) {
        if (etudiantRepository.existsById(id)) {
            etudiantRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Filiere not found");
        }
    }

}
