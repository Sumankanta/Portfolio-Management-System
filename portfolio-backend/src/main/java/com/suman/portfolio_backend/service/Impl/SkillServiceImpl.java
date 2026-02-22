package com.suman.portfolio_backend.service.Impl;

import com.suman.portfolio_backend.dto.SkillDTO;
import com.suman.portfolio_backend.entity.Skill;
import com.suman.portfolio_backend.repository.SkillRepository;
import com.suman.portfolio_backend.service.interfaces.SkillService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;
    private final ModelMapper modelMapper;

    @Override
    public SkillDTO createSkill(SkillDTO skillDTO) {
        log.info("Creating skill: {}", skillDTO.getName());

        Skill skill = modelMapper.map(skillDTO, Skill.class);
        Skill saved = skillRepository.save(skill);
        log.info("Skill created successfully with id: {}", saved.getId());
        return convertToDTO(saved);
    }

    @Override
    public SkillDTO updateSkill(Long id, SkillDTO skillDTO) {
        log.info("Updating skill ID: {}", id);

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Skill not found with id : {}", id);
                    return new RuntimeException("Skill not found");
                });

        skill.setName(skillDTO.getName());
        skill.setCategory(skillDTO.getCategory());
        skill.setProficiencyLevel(skillDTO.getProficiencyLevel());
        skill.setIconUrl(skillDTO.getIconUrl());

        log.info("Skill updated successfully with ID: {}", id);
        return convertToDTO(skill);
    }

    @Override
    public void deleteSkill(Long id) {
        log.warn("Attempting to deleteEmployment skill ID: {}", id);

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Skill not found with id : {}", id);
                    return new RuntimeException("Skill not found");
                });

        if (skill.isUsedInProjects()) {
            log.error("Cannot deleteEmployment skill ID {} because it is used in projects", id);
            throw new RuntimeException("Cannot deleteEmployment skill used in projects");
        }

        skillRepository.delete(skill);

        log.info("Skill deleted successfully ID: {}", id);
    }

    @Override
    public SkillDTO getSkillById(Long id) {
        log.debug("Fetching skill ID: {}", id);

        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Skill not found"));

        return convertToDTO(skill);
    }

    @Override
    public List<SkillDTO> getAllSkills() {
        return List.of();
    }

    private SkillDTO convertToDTO(Skill skill) {
        SkillDTO dto = modelMapper.map(skill, SkillDTO.class);
        dto.setProjectCount(skill.getProjectCount());

        return dto;
    }
}
