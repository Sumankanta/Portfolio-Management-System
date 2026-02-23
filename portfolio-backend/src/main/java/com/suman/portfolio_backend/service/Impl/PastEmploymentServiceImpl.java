package com.suman.portfolio_backend.service.Impl;

import com.suman.portfolio_backend.dto.PastEmploymentDTO;
import com.suman.portfolio_backend.entity.PastEmployment;
import com.suman.portfolio_backend.entity.User;
import com.suman.portfolio_backend.exception.ResourceNotFoundException;
import com.suman.portfolio_backend.repository.PastEmploymentRepository;
import com.suman.portfolio_backend.repository.UserRepository;
import com.suman.portfolio_backend.service.interfaces.PastEmploymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PastEmploymentServiceImpl implements PastEmploymentService {

    private final PastEmploymentRepository pastEmploymentRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public PastEmploymentDTO createEmployment(PastEmploymentDTO pastEmploymentDTO) {

        log.info("Creating employment for user ID: {}", pastEmploymentDTO.getUserId());

        User user = userRepository.findById(pastEmploymentDTO.getUserId())
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", pastEmploymentDTO.getUserId());
                    return new ResourceNotFoundException("User not found with ID: " + pastEmploymentDTO.getUserId());
                });

        PastEmployment employment = modelMapper.map(pastEmploymentDTO, PastEmployment.class);
        employment.setUser(user);

        PastEmployment saved = pastEmploymentRepository.save(employment);
        log.info("Employee created successfully with id: {}", saved.getId());

        return convertToDTO(saved);
    }

    @Override
    public PastEmploymentDTO updateEmployment(Long id, PastEmploymentDTO pastEmploymentDTO) {

        log.info("Updating employment ID: {}", id);

        PastEmployment employment = pastEmploymentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Employment not found with id : {}", id);
                    return new ResourceNotFoundException("Employment not found with ID: " + id);
                });

        employment.setCompanyName(pastEmploymentDTO.getCompanyName());
        employment.setJobRole(pastEmploymentDTO.getJobRole());
        employment.setStartDate(pastEmploymentDTO.getStartDate());
        employment.setEndDate(pastEmploymentDTO.getEndDate());
        employment.setIsCurrent(pastEmploymentDTO.getIsCurrent());
        employment.setDescription(pastEmploymentDTO.getDescription());

        log.info("Employment updated successfully with ID: {}", id);
        return convertToDTO(employment);
    }

    @Override
    public void deleteEmployment(Long id) {

        log.warn("Deleting employment ID: {}", id);

        PastEmployment employment = pastEmploymentRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Attempted to delete non-existing employment with ID: {}", id);
                    return new RuntimeException("Employment not found");
                });

        pastEmploymentRepository.delete(employment);
        log.info("Employment deleted successfully with ID: {}", id);
    }

    @Override
    public PastEmploymentDTO getById(Long id) {

        PastEmployment employment = pastEmploymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employment not found with ID: " + id));

        return convertToDTO(employment);
    }

    @Override
    public List<PastEmploymentDTO> getAll() {
        log.debug("Fetching all employment");
        return pastEmploymentRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(PastEmployment::getStartDate).reversed())
                .map(this::convertToDTO)
                .toList();
    }

    @Override
    public List<PastEmploymentDTO> getByUser(Long userId) {

        log.debug("Fetching employment records for user ID: {}", userId);
        return pastEmploymentRepository.findByUserId(userId)
                .stream()
                .sorted(Comparator.comparing(PastEmployment::getStartDate).reversed())
                .map(this::convertToDTO)
                .toList();
    }

    private PastEmploymentDTO convertToDTO(PastEmployment employment) {

        PastEmploymentDTO dto = modelMapper.map(employment, PastEmploymentDTO.class);

        dto.setUserId(employment.getUser().getId());
        dto.setFormattedDuration(employment.getFormattedDuration());
        dto.setDurationInMonths(employment.getDurationInMonths());

        return dto;
    }
}