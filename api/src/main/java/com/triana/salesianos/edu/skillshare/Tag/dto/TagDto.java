package com.triana.salesianos.edu.skillshare.Tag.dto;

import com.triana.salesianos.edu.skillshare.Tag.model.Tag;
import lombok.Builder;

import java.util.UUID;

@Builder
public record TagDto (
        UUID id,
        String name
) {
    public static TagDto of(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }
}
