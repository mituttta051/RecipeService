package cybercooker.recipeservice.repository.interfaces;

import cybercooker.recipeservice.entity.Tag;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;

import java.util.List;

public interface TagRepository {
    Tag getById(int id, int spaceId) throws NotFoundException;

    List<Tag> getAllBySpaceId(int spaceId);

    void save(Tag tag) throws AlreadyExistsException;

    void update(Tag tag) throws NotFoundException, AlreadyExistsException;

    void delete(int id, int spaceId) throws NotFoundException;
}
