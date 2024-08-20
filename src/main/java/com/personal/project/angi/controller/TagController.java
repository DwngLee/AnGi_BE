package com.personal.project.angi.controller;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.TagRequest;
import com.personal.project.angi.model.dto.response.TagResponse;
import com.personal.project.angi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping
    public ResponseEntity<ResponseDto<Void>> addTag(@RequestBody TagRequest tagRequest) {
        return tagService.addTag(tagRequest);
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<ResponseDto<Void>> removeTag(@PathVariable String tagId) {
        return tagService.removeTag(tagId);
    }

    @PutMapping("/{tagId}")
    public ResponseEntity<ResponseDto<Void>> updateTag(@PathVariable String tagId, @RequestBody TagRequest tagRequest) {
        return tagService.updateTag(tagId, tagRequest);
    }

    @GetMapping("/{tagId}")
    public ResponseEntity<ResponseDto<TagResponse>> getTag(@PathVariable String tagId) {
        return tagService.getTag(tagId);
    }

    @GetMapping
    public ResponseEntity<ResponseDto<List<TagResponse>>> getAllTags() {
        return tagService.getAllTags();
    }
}