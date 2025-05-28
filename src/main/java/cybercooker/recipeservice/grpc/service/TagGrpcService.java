package cybercooker.recipeservice.grpc.service;

import cybercooker.recipeservice.entity.Tag;
import cybercooker.recipeservice.grpc.tag.*;
import cybercooker.recipeservice.mapper.grpc.TagMapperGrpc;
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
    public void getTagById(TagId request, StreamObserver<TagGrpc> responseObserver) {
        Tag tag = tagService.getById(request.getId(), request.getSpaceId());
        TagGrpc tagGrpc = TagMapperGrpc.INSTANCE.toGrpc(tag);
        responseObserver.onNext(tagGrpc);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllTagsBySpaceId(TagGrpcGetAll request, StreamObserver<TagListGrpc> responseObserver) {
        TagListGrpc tagListGrpc = TagListGrpc.newBuilder()
                .addAllTags(tagService.getAllBySpaceId(request.getSpaceId()).stream()
                        .map(TagMapperGrpc.INSTANCE::toGrpc)
                        .collect(Collectors.toList()))
                .build();
        responseObserver.onNext(tagListGrpc);
        responseObserver.onCompleted();
    }

    @Override
    public void addTag(TagGrpcCreateRequest request, StreamObserver<Empty> responseObserver) {
        Tag tag = TagMapperGrpc.INSTANCE.fromCreateRequest(request);
        tagService.addTag(tag);

        responseObserver.onNext(Empty.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void updateTag(TagGrpc request, StreamObserver<Empty> responseObserver) {
        Tag tag = TagMapperGrpc.INSTANCE.fromUpdateRequest(request);
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
