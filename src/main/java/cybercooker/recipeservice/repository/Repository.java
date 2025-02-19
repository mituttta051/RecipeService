package cybercooker.recipeservice.repository;

import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;

import java.util.List;

@org.springframework.stereotype.Repository
public abstract class Repository<T> {
    public abstract T getById(int id, int spaceId) throws NotFoundException;

    public abstract List<T> getAllBySpaceId(int spaceId) throws NotFoundException;

    public abstract void save(T t) throws AlreadyExistsException;

    public abstract void update(T t) throws NotFoundException, AlreadyExistsException;

    public abstract void delete(int id, int spaceId) throws NotFoundException;
}
