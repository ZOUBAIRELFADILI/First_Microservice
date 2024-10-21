package com.microservice.etudiant.controller;

import com.microservice.etudiant.dto.EtudiantDTO;
import com.microservice.etudiant.entity.Etudiant;
import com.microservice.etudiant.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/etudiant")
public class EtudiantController {
    @Autowired
    EtudiantService etudiantService;
    @GetMapping("/")
    public List<EtudiantDTO> getAll(){
        return etudiantService.getAllEtudiant();
    }

    @GetMapping("/{id}")
    public EtudiantDTO getEtudinat(@PathVariable("id") Long id){
        return etudiantService.getEtudiant(id);
    }

    // New endpoint that returns both Etudiant and Filiere details
    @GetMapping("/{id}/filiere")
    public ResponseEntity<EtudiantDTO> getEtudiantWithFiliere(@PathVariable("id") Long id) {
        EtudiantDTO etudiantDTO = etudiantService.getEtudiant(id);
        return ResponseEntity.ok(etudiantDTO);
    }

    @PostMapping("/{add}")
    public Etudiant addEtudinat(@RequestBody EtudiantDTO filiereDTO){
        return etudiantService.addEtudiant(filiereDTO);
    }

    @PutMapping("/update/{id}")
    public Etudiant updateEtudiant(@PathVariable Long id, @RequestBody EtudiantDTO filiereDTO){
        return etudiantService.updateEtudiant(id,filiereDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteEtudiant(@PathVariable("id") Long id){
        etudiantService.deleteEtudiant(id);
    }
}

