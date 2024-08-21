package com.personal.project.angi.mapping;

import com.personal.project.angi.model.basemodel.OpenTimeBaseModel;
import com.personal.project.angi.model.dto.OpenTimeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OpenTimeMapper {
    OpenTimeBaseModel toOpenTimeBaseModel(OpenTimeDto openTimeDto);
    OpenTimeDto toOpenTimeDto(OpenTimeBaseModel openTimeBaseModel);
}
