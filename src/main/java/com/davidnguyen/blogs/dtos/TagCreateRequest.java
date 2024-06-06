package com.davidnguyen.blogs.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TagCreateRequest {
    @NotBlank(message = "Tag's name is required")
    @Size(min= 3, message = "Tag's name have at least 3 characters!")
    @Size(max= 100, message = "Tag's name have at most 100 characters!")
    private String name;
}
