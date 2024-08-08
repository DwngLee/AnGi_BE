package com.personal.project.angi.model.basemodel;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReviewBaseModel {
    @Id
    private String id;

    private LocalDateTime reviewDate;

    private String reviewTitle;

    private String reviewContent;

    private Double starNumber;

    private int likeNumber;

    private int unlikeNumber;

    private String userId;

    private List<String> reviewImageUrlList;

    @Field("createdAt")
    @CreatedDate
    private LocalDateTime createdAt;

    @Field("updatedAt")
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
