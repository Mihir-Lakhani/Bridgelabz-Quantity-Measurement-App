package com.bridgelabz.quantitymeasurement.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OperationRequest(
        @NotBlank String category,
        @NotNull Double firstValue,
        @NotBlank String firstUnit,
        Double secondValue,
        String secondUnit,
        String targetUnit) { }
