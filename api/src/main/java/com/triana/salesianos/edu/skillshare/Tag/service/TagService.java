package com.triana.salesianos.edu.skillshare.Tag.service;

import com.triana.salesianos.edu.skillshare.Tag.dto.TagDto;
import com.triana.salesianos.edu.skillshare.Tag.dto.TagRequest;
import com.triana.salesianos.edu.skillshare.Tag.model.Tag;
import com.triana.salesianos.edu.skillshare.Tag.repository.TagRepository;
import com.triana.salesianos.edu.skillshare.order.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@RequiredArgsConstructor
@Service
public class TagService {

    private final TagRepository tagRepository;
    private final OrderRepository orderRepository;

    public Set<Tag> addTags(Set<String> tags) {
        Set<Tag> result = new HashSet<>();
        for(String tag : tags) {
            Optional<Tag> findTag = tagRepository.findTagByName(tag);
            if(findTag.isPresent()) {
                result.add(findTag.get());
            } else if(tag != "" || tag != " "){
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

    public TagDto postTag(TagRequest name) {
        Tag result = Tag.builder()
                .id(UUID.randomUUID())
                .name(name.name())
                .build();
        tagRepository.save(result);
        return TagDto.of(result);
    }

    public Page<TagDto> getAllTags(Pageable pageable) {

        Page<Tag> tagPage = tagRepository.finAllTags(pageable);
        return tagPage.map(TagDto::of);
    }

    public TagDto getTag(String id) {
        Optional<Tag> findTag = tagRepository.findById(UUID.fromString(id));
        if(findTag.isPresent()) {
            return TagDto.of(findTag.get());
        } else {
            return null; //throw exception
        }
    }

    public TagDto putTag(String id, TagRequest name) {
        Optional<Tag> findTag = tagRepository.findById(UUID.fromString(id));
        if(findTag.isPresent()) {
            findTag.get().setName(name.name());
            tagRepository.save(findTag.get());
            return TagDto.of(findTag.get());
        } else {
            return null;//throw exception
        }
    }

    @Transactional
    public void deleteTag(String id) {
        Optional<Tag> findTag = tagRepository.findById(UUID.fromString(id));

        if(findTag.isPresent()) {
            tagRepository.deleteOrderTags(UUID.fromString(id));
            tagRepository.deleteById(UUID.fromString(id));
        } else {
            //throw exception
        }
    }
}
