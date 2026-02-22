package com.suman.portfolio_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {

    private Long id;
    private String name;
    private String category;
    private Integer proficiencyLevel;
    private String iconUrl;

    private Integer projectCount;
}
