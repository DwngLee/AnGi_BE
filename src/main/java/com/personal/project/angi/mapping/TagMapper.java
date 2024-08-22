package com.personal.project.angi.mapping;

import com.personal.project.angi.model.dto.TagSearchDto;
import com.personal.project.angi.model.dto.request.TagRequest;
import com.personal.project.angi.model.dto.request.UserUpdateInfoRequest;
import com.personal.project.angi.model.dto.response.TagResponse;
import com.personal.project.angi.model.enity.TagModel;
import com.personal.project.angi.model.enity.UserInfoModel;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagModel toTagModel(TagRequest tagRequest);
    TagResponse toTagResponse(TagModel tagModel);
    void updateTag(@MappingTarget TagModel userInfoModel, TagRequest userUpdateInfoRequest);
    default List<TagResponse> toTagResponseList(List<TagModel> tagModels) {
        return tagModels.stream()
                .map(this::toTagResponse)
                .collect(Collectors.toList());
    }

}
