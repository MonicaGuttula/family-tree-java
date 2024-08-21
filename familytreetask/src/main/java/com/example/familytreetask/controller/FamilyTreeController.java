package com.example.familytreetask.controller;

import com.example.familytreetask.model.FamilyTree;
import com.example.familytreetask.service.FamilyTreeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/familytree")
@CrossOrigin(origins = "http://localhost:4200")
public class FamilyTreeController {

    @Autowired
    private FamilyTreeService familyTreeService;

    @PostMapping("/generate")
    public FamilyTree generateFamilyTree(@RequestBody String input) {
        return familyTreeService.generateFamilyTree(input);
    }
}
