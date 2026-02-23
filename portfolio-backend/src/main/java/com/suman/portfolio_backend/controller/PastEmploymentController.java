package com.suman.portfolio_backend.controller;

import com.suman.portfolio_backend.dto.PastEmploymentDTO;
import com.suman.portfolio_backend.service.interfaces.PastEmploymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employments")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class PastEmploymentController {

    private final PastEmploymentService pastEmploymentService;

    @PostMapping
    public ResponseEntity<PastEmploymentDTO> createEmployment(@RequestBody PastEmploymentDTO pastEmploymentDTO) {
        log.info("REST request to Employment with Id: {}", pastEmploymentDTO.getId());
        PastEmploymentDTO created = pastEmploymentService.createEmployment(pastEmploymentDTO);
        log.info("Employment created successfully with ID: {}", created.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<PastEmploymentDTO>> getAll() {
        log.debug("REST request to fetch all employments");
        List<PastEmploymentDTO> pastEmployment = pastEmploymentService.getAll();
        log.info("Fetched {} employments", pastEmployment.size());
        return ResponseEntity.ok(pastEmployment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PastEmploymentDTO> getById(@PathVariable Long id) {
        log.debug("REST request to fetch employments with ID: {}", id);
        PastEmploymentDTO pastEmployment = pastEmploymentService.getById(id);
        return ResponseEntity.ok(pastEmployment);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PastEmploymentDTO>> getByUser(@PathVariable Long userId) {
        log.debug("REST request to fetch employments with useId: {}", userId);
        return ResponseEntity.ok(pastEmploymentService.getByUser(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PastEmploymentDTO> updateEmployment(
            @PathVariable Long id,
            @RequestBody PastEmploymentDTO dto) {
        log.info("REST request to updateEmployment employments with ID: {}", id);
        PastEmploymentDTO update = pastEmploymentService.updateEmployment(id, dto);
        log.info("Employments updated successfully with Id: {}", id);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployment(@PathVariable Long id) {
        log.info("REST request to deleteEmployment employments with ID: {}", id);
        pastEmploymentService.deleteEmployment(id);
        log.info("Employments deleted successfully with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}