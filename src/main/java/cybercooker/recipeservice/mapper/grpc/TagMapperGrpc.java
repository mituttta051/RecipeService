package cybercooker.recipeservice.mapper.grpc;

import cybercooker.recipeservice.entity.Tag;
import cybercooker.recipeservice.grpc.tag.TagGrpc;
import cybercooker.recipeservice.grpc.tag.TagGrpcCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Map;

@Mapper
public interface TagMapperGrpc {
    TagMapperGrpc INSTANCE = Mappers.getMapper(TagMapperGrpc.class);

    default TagGrpc toGrpc(Tag tag) {
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

    Tag fromCreateRequest(TagGrpcCreateRequest tagGrpc);

    Tag fromUpdateRequest(TagGrpc tagGrpc);

}
