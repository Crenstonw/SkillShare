package com.triana.salesianos.edu.skillshare.Tag.controller;

import com.triana.salesianos.edu.skillshare.Tag.dto.ListTagDto;
import com.triana.salesianos.edu.skillshare.Tag.dto.TagDto;
import com.triana.salesianos.edu.skillshare.Tag.dto.TagRequest;
import com.triana.salesianos.edu.skillshare.Tag.service.TagService;
import com.triana.salesianos.edu.skillshare.user.dto.EditUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200")
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @Operation(summary = "Post tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201",
                    description = "Post a new tag",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TagRequest.class))
                    )})
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PostMapping
    public ResponseEntity<TagDto> postTag(@RequestBody TagRequest name) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tagService.postTag(name));
    }

    @Operation(summary = "getAllTags")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Get all tags available",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public ResponseEntity<Page<TagDto>> getAllTags(@PageableDefault Pageable pageable) {
        return ResponseEntity.ok().body(tagService.getAllTags(pageable));
    }

    @Operation(summary = "Get tag by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Get a tag by its id",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TagDto> getTagById(@PathVariable String id) {
        return ResponseEntity.ok().body(tagService.getTag(id));
    }

    @Operation(summary = "Put tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Modify a tag",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = TagRequest.class))
                    )})
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<TagDto> putTag(@PathVariable String id, @RequestBody TagRequest name) {
        return ResponseEntity.ok().body(tagService.putTag(id, name));
    }

    @Operation(summary = "Delete tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="204",
                    description = "Delete a tag",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable String id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
