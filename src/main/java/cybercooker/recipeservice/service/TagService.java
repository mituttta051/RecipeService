package cybercooker.recipeservice.service;

import cybercooker.recipeservice.entity.Tag;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;
import cybercooker.recipeservice.repository.interfaces.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag getById(int id, int spaceId) throws NotFoundException {
        return tagRepository.getById(id, spaceId);
    }

    public List<Tag> getAllBySpaceId(int spaceId) {
        return tagRepository.getAllBySpaceId(spaceId);
    }

    public void addTag(Tag tag) throws AlreadyExistsException {
        tagRepository.save(tag);
    }

    public void updateTag(Tag tag) throws NotFoundException, AlreadyExistsException {
        tagRepository.update(tag);
    }

    public void deleteTag(int id, int spaceId) throws NotFoundException {
        tagRepository.delete(id, spaceId);
    }

}
