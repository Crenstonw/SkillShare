package com.triana.salesianos.edu.skillshare.Tag.controller;

import com.triana.salesianos.edu.skillshare.Tag.dto.ListTagDto;
import com.triana.salesianos.edu.skillshare.Tag.dto.TagDto;
import com.triana.salesianos.edu.skillshare.Tag.dto.TagRequest;
import com.triana.salesianos.edu.skillshare.Tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @PostMapping
    public ResponseEntity<TagDto> postTag(@RequestBody TagRequest name) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.postTag(name));
    }

    @GetMapping
    public ResponseEntity<Page<TagDto>> getAllTags(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().body(tagService.getAllTags(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable String id) {
        return ResponseEntity.ok().body(tagService.getTag(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDto> putTag(@PathVariable String id, @RequestBody TagRequest name) {
        return ResponseEntity.ok().body(tagService.putTag(id, name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable String id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
