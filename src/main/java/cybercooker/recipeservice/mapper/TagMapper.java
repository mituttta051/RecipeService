package cybercooker.recipeservice.mapper;

import cybercooker.recipeservice.entity.Tag;
import cybercooker.recipeservice.grpc.tag.TagCreateRequest;
import cybercooker.recipeservice.grpc.tag.TagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    default TagDTO toTagDTO(Tag tag) {
        if (tag == null) {
            return null;
        }

        TagDTO.Builder tagDTO = TagDTO.newBuilder();

        tagDTO.setId(tag.getId());
        tagDTO.setSpaceId(tag.getSpaceId());
        tagDTO.setName(tag.getName());
        if (tagDTO.getValuesMap() != null) {
            Map<Integer, String> map = tag.getValues();
            if (map != null) {
                tagDTO.putAllValues(map);
            }
        }

        return tagDTO.build();
    }

    Tag fromRequestToTag(TagCreateRequest tagDTO);

    Tag fromDTOToTag(TagDTO tagDTO);

}
