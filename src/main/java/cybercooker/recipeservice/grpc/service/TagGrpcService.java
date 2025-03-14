package cybercooker.recipeservice.grpc.service;

import cybercooker.recipeservice.entity.Tag;
import cybercooker.recipeservice.grpc.tag.*;
import cybercooker.recipeservice.mapper.TagMapper;
import cybercooker.recipeservice.service.TagService;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.grpc.server.service.GrpcService;

import java.util.stream.Collectors;

@GrpcService
public class TagGrpcService extends TagServiceGrpc.TagServiceImplBase {
    @Autowired
    private TagService tagService;

    @Override
    public void getTagById(TagId request, StreamObserver<TagDTO> responseObserver) {
        Tag tag = tagService.getById(request.getId(), request.getSpaceId());
        TagDTO tagDTO = TagMapper.INSTANCE.toTagDTO(tag);
        responseObserver.onNext(tagDTO);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllTagsBySpaceId(TagGetAllBySpaceIdRequest request, StreamObserver<TagListDTO> responseObserver) {
        TagListDTO tagListDTO = TagListDTO.newBuilder()
                .addAllTags(tagService.getAllBySpaceId(request.getSpaceId()).stream()
                        .map(TagMapper.INSTANCE::toTagDTO)
                        .collect(Collectors.toList()))
                .build();
        responseObserver.onNext(tagListDTO);
        responseObserver.onCompleted();
    }

    @Override
    public void addTag(TagCreateRequest request, StreamObserver<Empty> responseObserver) {
        Tag tag = TagMapper.INSTANCE.fromRequestToTag(request);
        tagService.addTag(tag);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateTag(TagDTO request, StreamObserver<Empty> responseObserver) {
        Tag tag = TagMapper.INSTANCE.fromDTOToTag(request);
        tagService.updateTag(tag);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTagById(TagId request, StreamObserver<Empty> responseObserver) {
        tagService.deleteTag(request.getId(), request.getSpaceId());

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }
}
