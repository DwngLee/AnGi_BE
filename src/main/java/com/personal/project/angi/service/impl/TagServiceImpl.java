package com.personal.project.angi.service.impl;

import com.personal.project.angi.enums.MessageResponseEnum;
import com.personal.project.angi.enums.ResponseCodeEnum;
import com.personal.project.angi.exception.response.ResponseBuilder;
import com.personal.project.angi.mapping.TagMapper;
import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.TagRequest;
import com.personal.project.angi.model.dto.response.TagResponse;
import com.personal.project.angi.model.enity.TagModel;
import com.personal.project.angi.repository.TagRepository;
import com.personal.project.angi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;


    @Override
    public ResponseEntity<ResponseDto<Void>> addTag(TagRequest tag) {
        try {
            TagModel tagModel = tagMapper.toTagModel(tag);
            tagRepository.save(tagModel);
            return ResponseBuilder.okResponse(
                    MessageResponseEnum.CREATE_TAG_SUCCESS.getMessage(),
                    ResponseCodeEnum.CREATETAG1200);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.CREATE_TAG_FAILED.getMessage(),
                    ResponseCodeEnum.CREATETAG0200);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<Void>> removeTag(String tagId) {
        try {
            tagRepository.deleteById(tagId);
            return ResponseBuilder.okResponse(
                    MessageResponseEnum.DELETE_TAG_SUCCESS.getMessage(),
                    ResponseCodeEnum.DELETETAG1200);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.DELETE_TAG_FAILED.getMessage(),
                    ResponseCodeEnum.DELETETAG0200);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<Void>> updateTag(String id, TagRequest newTag) {
        try {
            TagModel existingTag = tagRepository.findById(id).orElse(null);
            if (existingTag == null) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.UPDATE_TAG_FAILED.getMessage(),
                        ResponseCodeEnum.UPDATETAG0201);
            }
            tagMapper.updateTag(existingTag, newTag);
            tagRepository.save(existingTag);
            return ResponseBuilder.okResponse(
                    MessageResponseEnum.UPDATE_TAG_SUCCESS.getMessage(),
                    ResponseCodeEnum.UPDATETAG1200);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.UPDATE_TAG_FAILED.getMessage(),
                    ResponseCodeEnum.UPDATETAG0200);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<TagResponse>> getTag(String id) {
        try {
            TagModel tagModel = tagRepository.findById(id).orElse(null);
            if (tagModel == null) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.GET_TAG_FAILED.getMessage(),
                        ResponseCodeEnum.GETTAG0200);
            }
            TagResponse tagResponse = tagMapper.toTagResponse(tagModel);
            return ResponseBuilder.okResponse(
                    MessageResponseEnum.GET_TAG_SUCCESS.getMessage(),
                    tagResponse,
                    ResponseCodeEnum.GETTAG1200);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.GET_TAG_FAILED.getMessage(),
                    ResponseCodeEnum.GETTAG0200);
        }
    }

    @Override
    public ResponseEntity<ResponseDto<List<TagResponse>>> getAllTags() {
        try {
            List<TagModel> tagModels = tagRepository.findAll();
            List<TagResponse> tagResponses = tagMapper.toTagResponseList(tagModels);
            return ResponseBuilder.okResponse(
                    MessageResponseEnum.GET_ALL_TAGS_SUCCESS.getMessage(),
                    tagResponses,
                    ResponseCodeEnum.GETALLTAGS1200);
        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.GET_ALL_TAGS_FAILED.getMessage(),
                    ResponseCodeEnum.GETALLTAGS0200);
        }
    }
}
