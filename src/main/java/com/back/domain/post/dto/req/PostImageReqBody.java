package com.back.domain.post.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record PostImageReqBody(
        @NotBlank String imageUrl,
        @NotNull(message = "대표 이미지 여부를 입력하세요")
        Boolean isPrimary
) {
}
