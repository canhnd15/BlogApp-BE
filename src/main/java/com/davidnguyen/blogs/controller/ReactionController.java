package com.davidnguyen.blogs.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reactions")
@Tag(name = "Reaction", description = "API for managing post's reactions")
public class ReactionController {
}
