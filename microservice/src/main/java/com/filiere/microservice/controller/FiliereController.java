package com.filiere.microservice.controller;

import com.filiere.microservice.dto.FiliereDTO;
import com.filiere.microservice.entity.Filiere;
import com.filiere.microservice.service.FiliereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/filiere")
public class FiliereController {
    @Autowired
    FiliereService filiereService;
    @GetMapping("/")
    public List<FiliereDTO> getAll(){
        return filiereService.getAllFiliere();
    }

    @GetMapping("/{id}")
    public FiliereDTO getFiliere(@PathVariable("id") Long id){
        return filiereService.getFiliere(id);
    }

    @PostMapping("/{add}")
    public Filiere addFiliere(@RequestBody FiliereDTO filiereDTO){
        return filiereService.addFiliere(filiereDTO);
    }

    @PutMapping("/update/{id}")
    public Filiere updateFiliere(@PathVariable Long id, @RequestBody FiliereDTO filiereDTO){
        return filiereService.updateFiliere(id,filiereDTO);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFiliere(@PathVariable("id") Long id){
        filiereService.deleteFiliere(id);
    }
}
