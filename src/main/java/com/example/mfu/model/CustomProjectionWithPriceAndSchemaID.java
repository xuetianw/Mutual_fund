package com.example.mfu.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomProjectionWithPriceAndSchemaID {
    private PortfolioProjection portfolioProjection;
    private Integer schemaId;
    private Double currPrice;

}
