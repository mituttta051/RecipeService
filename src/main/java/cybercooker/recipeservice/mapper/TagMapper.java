package cybercooker.recipeservice.mapper;

import cybercooker.recipeservice.entity.Tag;
import cybercooker.recipeservice.grpc.tag.TagGrpc;
import cybercooker.recipeservice.grpc.tag.TagGrpcCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    default TagGrpc toTagGrpc(Tag tag) {
        if (tag == null) {
            return null;
        }

        TagGrpc.Builder tagGrpc = TagGrpc.newBuilder();

        tagGrpc.setId(tag.getId());
        tagGrpc.setSpaceId(tag.getSpaceId());
        tagGrpc.setName(tag.getName());
        if (tagGrpc.getValuesMap() != null) {
            Map<Integer, String> map = tag.getValues();
            if (map != null) {
                tagGrpc.putAllValues(map);
            }
        }

        return tagGrpc.build();
    }

    Tag fromRequestToTag(TagGrpcCreateRequest tagGrpc);

    Tag fromGrpcToTag(TagGrpc tagGrpc);

}
