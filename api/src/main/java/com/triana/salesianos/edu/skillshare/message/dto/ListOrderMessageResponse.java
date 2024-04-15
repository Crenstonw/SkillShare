package com.triana.salesianos.edu.skillshare.message.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ListOrderMessageResponse(
        List<OrderMessageResponse> orderMessageResponses
) {
    public static ListOrderMessageResponse of(List<OrderMessageResponse> messageResponse) {
        return ListOrderMessageResponse.builder()
                .orderMessageResponses(messageResponse)
                .build();
    }
}
