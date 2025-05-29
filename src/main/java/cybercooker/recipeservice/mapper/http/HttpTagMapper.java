package cybercooker.recipeservice.mapper.http;

import cybercooker.recipeservice.entity.Tag;
import cybercooker.recipeservice.request.tag.CreateTagRequest;
import cybercooker.recipeservice.request.tag.UpdateTagRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HttpTagMapper {
    HttpTagMapper INSTANCE = Mappers.getMapper(HttpTagMapper.class);

    Tag fromCreateRequest(CreateTagRequest request);

    Tag fromUpdateRequest(UpdateTagRequest request);
}

