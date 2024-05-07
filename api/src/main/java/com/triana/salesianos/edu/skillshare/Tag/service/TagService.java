package com.triana.salesianos.edu.skillshare.Tag.service;

import com.triana.salesianos.edu.skillshare.Tag.dto.ListTagDto;
import com.triana.salesianos.edu.skillshare.Tag.dto.TagDto;
import com.triana.salesianos.edu.skillshare.Tag.model.Tag;
import com.triana.salesianos.edu.skillshare.Tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;

    public Set<Tag> addTags(Set<String> tags) {
        Set<Tag> result = new HashSet<>();
        for(String tag : tags) {
            Optional<Tag> findTag = tagRepository.findTagByName(tag);
            if(findTag.isPresent()) {
                result.add(findTag.get());
            } else {
                Tag newTag = Tag.builder()
                        .id(UUID.randomUUID())
                        .name(tag)
                        .build();
                tagRepository.save(newTag);
                result.add(newTag);
            }
        }
        return result;
    }

    public ListTagDto getAllTags() {
        List<Tag> allTags = tagRepository.findAll();
        List<TagDto> result = new ArrayList<>();
        for(Tag tag : allTags) {
            result.add(TagDto.of(tag));
        }
        return ListTagDto.of(result);
    }
}
