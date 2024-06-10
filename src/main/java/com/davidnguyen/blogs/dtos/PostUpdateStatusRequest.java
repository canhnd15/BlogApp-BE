package com.davidnguyen.blogs.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostUpdateStatusRequest {
    @NotNull(message = "Status is required")
    @NotBlank(message = "Status is not blank")
    private String status;
}
