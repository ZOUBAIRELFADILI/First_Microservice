package com.microservice.etudiant.controller;

import com.microservice.etudiant.dto.EtudiantDTO;
import com.microservice.etudiant.entity.Etudiant;
import com.microservice.etudiant.service.EtudiantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

