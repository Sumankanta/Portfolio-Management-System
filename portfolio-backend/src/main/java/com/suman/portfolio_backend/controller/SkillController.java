package com.suman.portfolio_backend.controller;

import com.suman.portfolio_backend.dto.SkillDTO;
import com.suman.portfolio_backend.service.interfaces.SkillService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class SkillController {

    private final SkillService skillService;

    @PostMapping
    public ResponseEntity<SkillDTO> createSkill(@RequestBody SkillDTO skillDTO) {
        log.info("REST request to createEmployment skill with Id: {}", skillDTO.getId());
        SkillDTO created = skillService.createSkill(skillDTO);
        log.info("Skill created successfully with ID: {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<SkillDTO>> getAll() {
        log.debug("REST request to fetch all skills");
        List<SkillDTO> skills = skillService.getAllSkills();
        log.info("Fetched {} skills", skills.size());
        return ResponseEntity.ok(skills);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SkillDTO> getById(@PathVariable Long id) {
        log.debug("REST request to fetch skills with ID: {}", id);
        SkillDTO skills = skillService.getSkillById(id);
        return ResponseEntity.ok(skills);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SkillDTO> updateSkill(@PathVariable Long id, @RequestBody SkillDTO skillDTO) {
        log.info("REST request to updateEmployment skill with ID: {}", id);
        SkillDTO update = skillService.updateSkill(id, skillDTO);
        log.info("Skills updated successfully with Id: {}", id);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.info("REST request to deleteEmployment skills with ID: {}", id);
        skillService.deleteSkill(id);
        log.info("Skills deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}