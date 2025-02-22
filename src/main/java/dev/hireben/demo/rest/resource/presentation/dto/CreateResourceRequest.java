package dev.hireben.demo.rest.resource.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record CreateResourceRequest(
    @NotBlank String field1,
    @NotBlank String field2,
    @NotBlank String field3) {
}
