package com.microservice.etudiant.service;

import com.microservice.etudiant.dto.EtudiantDTO;
import com.microservice.etudiant.entity.Etudiant;
import com.microservice.etudiant.mappers.EtudiantMapper;
import com.microservice.etudiant.repository.EtudiantRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EtudiantServiceImp implements EtudiantService {
    private static final Logger logger = LoggerFactory.getLogger(EtudiantServiceImp.class);

    final EtudiantRepository etudiantRepository;
    final EtudiantMapper etudiantMapper;
    final RestTemplate restTemplate;

    //@Value("${filiere.service.url}") // Externalize the Filiere service URL in application.properties
    private String filiereServiceUrl = "http://localhost:8080/api/filiere";

    public EtudiantServiceImp(EtudiantRepository etudiantRepository, EtudiantMapper etudiantMapper, RestTemplate restTemplate) {
        this.etudiantRepository = etudiantRepository;
        this.etudiantMapper = etudiantMapper;
        this.restTemplate = restTemplate;
    }


    @Override
    public EtudiantDTO getEtudiant(Long id) {
        Optional<Etudiant> etudiantOptional = etudiantRepository.findById(id);
        if (etudiantOptional.isPresent()) {
            Etudiant etudiant = etudiantOptional.get();

            // Fetch Filiere details using RestTemplate based on idFiliere
            Long idFiliere = etudiant.getIdFiliere();
            if (idFiliere != null) {
                String filiereUrl = filiereServiceUrl + "/" + idFiliere;
                ResponseEntity<Object> filiereResponse = restTemplate.getForEntity(filiereUrl, Object.class);

                // Log or process the fetched Filiere information (if needed)
                // For now, we'll just keep idFiliere in EtudiantDTO without further processing
                // If you want to store more details, you'd map the response properly
            }

            // Map Etudiant to EtudiantDTO and return it
            return etudiantMapper.toDTO(etudiant);
        } else {
            throw new EntityNotFoundException("Etudiant not found");
        }
    }

    @Override
    public List<EtudiantDTO> getAllEtudiant() {
        List<EtudiantDTO> etudiantDTOList = new ArrayList<>();
        List<Etudiant> etudiants = etudiantRepository.findAll();
        for (Etudiant e : etudiants) {
            etudiantDTOList.add(etudiantMapper.toDTO(e));
        }
        return etudiantDTOList;
    }

    @Override
    public Etudiant addEtudiant(EtudiantDTO etudiantDTO) {
        // Check if Filiere exists
        String filiereUrl = filiereServiceUrl + "/" + etudiantDTO.getIdFiliere();
        logger.info("Connecting to Filiere service at URL: {}", filiereUrl);

        try {
            ResponseEntity<Object> filiereResponse = restTemplate.getForEntity(filiereUrl, Object.class);
            if (filiereResponse.getStatusCode().is2xxSuccessful()) {
                logger.info("Filiere with ID {} found.", etudiantDTO.getIdFiliere());
                // Filiere exists, proceed to save Etudiant
                Etudiant etudiant = etudiantMapper.fromDTO(etudiantDTO);
                etudiantRepository.save(etudiant);
                return etudiant;
            }
        } catch (Exception e) {
            logger.error("Failed to connect to Filiere service or Filiere not found: {}", e.getMessage());
            throw new EntityNotFoundException("Filiere with ID " + etudiantDTO.getIdFiliere() + " not found.");
        }

        return null; // Or handle error case properly
    }

    @Override
    public Etudiant updateEtudiant (Long id, EtudiantDTO etudiantDTO) {
        Optional<Etudiant> optionalEtudiant = etudiantRepository.findById(id);

        if (optionalEtudiant.isPresent()) {
            Etudiant existingEtudiant = optionalEtudiant.get();

            // Manually map the properties that need to be updated
            existingEtudiant.setNom(etudiantDTO.getNom());
            existingEtudiant.setPrenom(etudiantDTO.getPrenom());
            existingEtudiant.setDateNaissance(etudiantDTO.getDateNaissance());
            existingEtudiant.setTelephone(etudiantDTO.getTelephone());
            existingEtudiant.setEmail(etudiantDTO.getEmail());
            existingEtudiant.setIdFiliere(etudiantDTO.getIdFiliere());

            // Save the updated entity
            etudiantRepository.save(existingEtudiant);

            return existingEtudiant;
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
