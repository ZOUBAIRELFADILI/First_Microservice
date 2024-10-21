package com.filiere.microservice.dto;

import com.filiere.microservice.enums.Cycle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiliereDTO {
    private Long id;
    private String titre;
    private Cycle cycle;
}
