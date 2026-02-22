package com.suman.portfolio_backend.service.interfaces;

import com.suman.portfolio_backend.dto.PastEmploymentDTO;

import java.util.List;

public interface PastEmploymentService {

    PastEmploymentDTO createEmployment(PastEmploymentDTO pastEmploymentDTO);
    PastEmploymentDTO updateEmployment(Long id, PastEmploymentDTO pastEmploymentDTO);
    void deleteEmployment(Long id);
    PastEmploymentDTO getById(Long id);
    List<PastEmploymentDTO> getAll();
    List<PastEmploymentDTO> getByUser(Long userId);
}
