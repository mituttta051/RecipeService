package cybercooker.recipeservice.mapper.grpc;

import cybercooker.recipeservice.entity.filter.*;
import cybercooker.recipeservice.grpc.filter.*;

import java.util.stream.Collectors;

public class GrpcFilterMapper {

    public static Filter map(FilterGrpc grpcFilter) {
        return switch (grpcFilter.getFilterCase()) {
            case CONTAINSTAG -> toContainsTagFilter(grpcFilter.getContainsTag());
            case OR -> toOrFilter(grpcFilter.getOr());
            case AND -> toAndFilter(grpcFilter.getAnd());
            case COOKTIME -> toCookTimeFilter(grpcFilter.getCookTime());
            case SERVINGSNUMBER -> toServingsNumberFilter(grpcFilter.getServingsNumber());
            default -> throw new IllegalArgumentException("Unknown filter type: " + grpcFilter.getFilterCase());
        };
    }

    public static OrFilter toOrFilter(OrFilterGrpc orFilterGrpc) {
        return OrFilter.builder()
                .filters(orFilterGrpc.getFiltersList().stream()
                        .map(GrpcFilterMapper::map)
                        .collect(Collectors.toList()))
                .build();
    }

    public static AndFilter toAndFilter(AndFilterGrpc andFilterGrpc) {
        return AndFilter.builder()
                .filters(andFilterGrpc.getFiltersList().stream()
                        .map(GrpcFilterMapper::map)
                        .collect(Collectors.toList()))
                .build();
    }

    public static ContainsTagFilter toContainsTagFilter(ContainsTagFilterGrpc containsTagFilterGrpc) {
        return ContainsTagFilter.builder()
                .tagId(containsTagFilterGrpc.getTagId())
                .tagValue(containsTagFilterGrpc.getTagValue())
                .build();
    }

    public static CookTimeFilter toCookTimeFilter(CookTimeFilterGrpc cookTimeFilterGrpc) {
        return CookTimeFilter.builder()
                .minCookTime(cookTimeFilterGrpc.getMin())
                .maxCookTime(cookTimeFilterGrpc.getMax())
                .build();
    }

    public static ServingsNumberFilter toServingsNumberFilter(ServingsNumberFilterGrpc servingsNumberFilterGrpc) {
        return ServingsNumberFilter.builder()
                .minServingsNumber(servingsNumberFilterGrpc.getMin())
                .maxServingsNumber(servingsNumberFilterGrpc.getMax())
                .build();
    }

}
