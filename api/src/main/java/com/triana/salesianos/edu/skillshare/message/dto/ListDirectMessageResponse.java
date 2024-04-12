package com.triana.salesianos.edu.skillshare.message.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ListDirectMessageResponse(
        List<DirectMessageResponse> directMessages
) {
    public static ListDirectMessageResponse of(List<DirectMessageResponse> directMessageResponses) {
        return ListDirectMessageResponse.builder()
                .directMessages(directMessageResponses)
                .build();
    }
}
