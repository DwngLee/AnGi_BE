package com.personal.project.angi.service;

import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.TagRequest;
import com.personal.project.angi.model.dto.response.TagResponse;
import com.personal.project.angi.model.enity.TagModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagService {
    ResponseEntity<ResponseDto<Void>> addTag(TagRequest tag);
    ResponseEntity<ResponseDto<Void>>  removeTag(String tagId);
    ResponseEntity<ResponseDto<Void>> updateTag(String id, TagRequest newTag);
    ResponseEntity<ResponseDto<TagResponse>>  getTag(String id);
    ResponseEntity<ResponseDto<List<TagResponse>>> getAllTags();
    TagModel getTagModel(String id);
}
