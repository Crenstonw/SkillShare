package com.triana.salesianos.edu.skillshare.Tag.dto;

import lombok.Builder;

import java.util.List;
@Builder
public record ListTagDto(
        List<TagDto> tagList
) {

    public static ListTagDto of(List<TagDto> tags) {
        return ListTagDto.builder()
                .tagList(tags)
                .build();
    }
}
