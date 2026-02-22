package com.suman.portfolio_backend.service.interfaces;

import com.suman.portfolio_backend.dto.SkillDTO;

import java.util.List;

public interface SkillService {
    SkillDTO createSkill(SkillDTO skillDTO);
    SkillDTO updateSkill(Long id, SkillDTO skillDTO);
    void deleteSkill(Long id);
    SkillDTO getSkillById(Long id);
    List<SkillDTO> getAllSkills();
}
