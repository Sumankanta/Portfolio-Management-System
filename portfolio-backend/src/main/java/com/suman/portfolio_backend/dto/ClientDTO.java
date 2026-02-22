package com.suman.portfolio_backend.dto;

import lombok.Data;

@Data
public class ClientDTO {

    private Long id;
    private String name;
    private String logoUrl;
    private String websiteUrl;
    private String description;

    private int projectCount;
}
