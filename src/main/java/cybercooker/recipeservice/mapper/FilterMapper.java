package cybercooker.recipeservice.mapper;

import cybercooker.recipeservice.entity.filter.AndFilter;
import cybercooker.recipeservice.entity.filter.ContainsTagFilter;
import cybercooker.recipeservice.entity.filter.Filter;
import cybercooker.recipeservice.entity.filter.OrFilter;
import cybercooker.recipeservice.grpc.filter.AndFilterGrpc;
import cybercooker.recipeservice.grpc.filter.ContainsTagFilterGrpc;
import cybercooker.recipeservice.grpc.filter.FilterGrpc;
import cybercooker.recipeservice.grpc.filter.OrFilterGrpc;

import java.util.stream.Collectors;

public class FilterMapper {
    
    public static Filter map(FilterGrpc grpcFilter) {
        return switch (grpcFilter.getFilterCase()) {
            case CONTAINSTAG -> toContainsTagFilter(grpcFilter.getContainsTag());
            case OR -> toOrFilter(grpcFilter.getOr());
            case AND -> toAndFilter(grpcFilter.getAnd());
            default -> throw new IllegalArgumentException("Unknown filter type: " + grpcFilter.getFilterCase());
        };
    }
    
    public static OrFilter toOrFilter(OrFilterGrpc orFilterGrpc) {
        return OrFilter.builder()
                .filters(orFilterGrpc.getFiltersList().stream()
                        .map(FilterMapper::map)
                        .collect(Collectors.toList()))
                .build();
    }
    
    public static AndFilter toAndFilter(AndFilterGrpc andFilterGrpc) {
        return AndFilter.builder()
                .filters(andFilterGrpc.getFiltersList().stream()
                        .map(FilterMapper::map)
                        .collect(Collectors.toList()))
                .build();
    }
    
    public static ContainsTagFilter toContainsTagFilter(ContainsTagFilterGrpc containsTagFilterGrpc) {
        return ContainsTagFilter.builder()
                .tagId(containsTagFilterGrpc.getTagId())
                .tagValue(containsTagFilterGrpc.getTagValue())
                .build();
    }
    
}
