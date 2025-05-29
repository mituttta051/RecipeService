package cybercooker.recipeservice.controller;

import cybercooker.recipeservice.entity.Tag;
import cybercooker.recipeservice.mapper.http.HttpTagMapper;
import cybercooker.recipeservice.request.tag.CreateTagRequest;
import cybercooker.recipeservice.request.tag.UpdateTagRequest;
import cybercooker.recipeservice.service.TagService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/{spaceId}/{id}")
    public ResponseEntity<Tag> getTagById(@PathVariable int spaceId, @PathVariable int id) {
        Tag tag = tagService.getById(id, spaceId);
        return ResponseEntity.ok(tag);
    }

    @GetMapping("/{spaceId}")
    public ResponseEntity<List<Tag>> getAllTagsBySpaceId(@PathVariable int spaceId) {
        return ResponseEntity.ok(
                tagService.getAllBySpaceId(spaceId).stream().collect(Collectors.toList())
        );
    }

    @PostMapping
    public void addTag(@Valid @RequestBody CreateTagRequest request) {
        Tag tag = HttpTagMapper.INSTANCE.fromCreateRequest(request);
        tagService.addTag(tag);
    }

    @PutMapping
    public void updateTag(@Valid @RequestBody UpdateTagRequest request) {
        Tag tag = HttpTagMapper.INSTANCE.fromUpdateRequest(request);
        tagService.updateTag(tag);
    }

    @DeleteMapping("/{spaceId}/{id}")
    public void deleteTagById(@PathVariable int spaceId, @PathVariable int id) {
        tagService.deleteTag(id, spaceId);
    }
}
