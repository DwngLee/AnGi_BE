package com.personal.project.angi.service.impl;

import com.personal.project.angi.configuration.security.UserDetailsImpl;
import com.personal.project.angi.constant.UploadConstant;
import com.personal.project.angi.enums.MessageResponseEnum;
import com.personal.project.angi.enums.ResponseCodeEnum;
import com.personal.project.angi.exception.response.ResponseBuilder;
import com.personal.project.angi.mapping.RestaurantMapper;
import com.personal.project.angi.model.basemodel.TagBaseModel;
import com.personal.project.angi.model.dto.ResponseDto;
import com.personal.project.angi.model.dto.request.RestaurantCreationRequest;
import com.personal.project.angi.model.enity.RestaurantElkModel;
import com.personal.project.angi.model.enity.RestaurantModel;
import com.personal.project.angi.model.enity.TagModel;
import com.personal.project.angi.model.enity.UserInfoModel;
import com.personal.project.angi.repository.RestaurantRepository;
import com.personal.project.angi.service.FileService;
import com.personal.project.angi.service.RestaurantElkService;
import com.personal.project.angi.service.RestaurantService;
import com.personal.project.angi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final FileService fileService;
    private final TagService tagService;
    private final RestaurantElkService restaurantElkService;

    @Override
    public ResponseEntity<ResponseDto<Void>> createRestaurant(RestaurantCreationRequest request) {
        try {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (userDetails == null) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.CREATE_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.CREATERESTAURANT0201);
            }
            UserInfoModel user = userDetails.getUser();
            RestaurantModel restaurantModel = null;

            //Generate id for create file path
            String restaurantId = ObjectId.get().toString();

            try {
                restaurantModel = restaurantMapper.toRestaurantModel(request);
            } catch (Exception e) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.CREATE_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.CREATERESTAURANT0202);
            }

            restaurantModel.setId(restaurantId);
            restaurantModel.setUserAddId(user.getId());
            restaurantModel.setUserUpdateId(user.getId());

            //Upload images
            List<String> imageUrlList = new ArrayList<>();
            if (request.getRestaurantImageList() != null) {
                try {
                    List<Map> results = fileService.uploadMultiFiles(request.getRestaurantImageList(), restaurantId, UploadConstant.RESTAURANT_IMAGE);

                    for (Map result : results) {
                        imageUrlList.add((String) result.get("url"));
                    }
                } catch (Exception e) {
                    return ResponseBuilder.badRequestResponse(
                            MessageResponseEnum.CREATE_RESTAURANT_FAILED.getMessage(),
                            ResponseCodeEnum.CREATERESTAURANT0203);
                }
                restaurantModel.setRestaurantImageUrlList(imageUrlList);
            }

            //Find and add tags
            List<TagBaseModel> tagList = getTagModelList(request.getTagIdList());

            RestaurantElkModel restaurantElkModel = restaurantMapper.toRestaurantElkModel(restaurantModel);
            restaurantElkModel.setTagBaseModelList(tagList);

            try {
                restaurantRepository.save(restaurantModel);
                restaurantElkService.saveOrUpdateRestaurant(restaurantElkModel);

            } catch (Exception e) {
                return ResponseBuilder.badRequestResponse(
                        MessageResponseEnum.CREATE_RESTAURANT_FAILED.getMessage(),
                        ResponseCodeEnum.CREATERESTAURANT0204);
            }
            return ResponseBuilder.okResponse(
                    MessageResponseEnum.CREATE_RESTAURANT_SUCCESS.getMessage(),
                    ResponseCodeEnum.CREATERESTAURANT1200);


        } catch (Exception e) {
            return ResponseBuilder.badRequestResponse(
                    MessageResponseEnum.CREATE_RESTAURANT_FAILED.getMessage(),
                    ResponseCodeEnum.CREATERESTAURANT0200);
        }
    }


    private List<TagBaseModel> getTagModelList(List<String> tagIdList) {
        List<TagBaseModel> tagList = new ArrayList<>();
        for (String tagId : tagIdList) {
            TagModel tag = tagService.getTagModel(tagId);
            if (tag != null) {
                tagList.add(tag);
            }
        }
        return tagList;
    }
}
