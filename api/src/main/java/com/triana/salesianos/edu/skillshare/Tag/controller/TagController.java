package com.triana.salesianos.edu.skillshare.Tag.controller;

import com.triana.salesianos.edu.skillshare.Tag.dto.ListTagDto;
import com.triana.salesianos.edu.skillshare.Tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<ListTagDto> getAllTags() {
        return ResponseEntity.ok().body(tagService.getAllTags());
    }
}
