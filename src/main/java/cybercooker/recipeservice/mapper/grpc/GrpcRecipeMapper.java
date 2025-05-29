package cybercooker.recipeservice.mapper.grpc;

import cybercooker.recipeservice.entity.Recipe;
import cybercooker.recipeservice.grpc.recipe.RecipeGrpc;
import cybercooker.recipeservice.grpc.recipe.RecipeGrpcCreateRequest;
import cybercooker.recipeservice.grpc.recipe.TagGrpc;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface GrpcRecipeMapper {
    GrpcRecipeMapper INSTANCE = Mappers.getMapper(GrpcRecipeMapper.class);


    default RecipeGrpc toGrpc(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        RecipeGrpc.Builder recipeGrpc = RecipeGrpc.newBuilder();

        recipeGrpc.setId(recipe.getId());
        recipeGrpc.setSpaceId(recipe.getSpaceId());
        recipeGrpc.setName(recipe.getName());
        recipeGrpc.setDescription(recipe.getDescription());
        recipeGrpc.setServingsNumber(recipe.getServingsNumber());
        recipeGrpc.setCookTime(recipe.getCookTime());
        recipeGrpc.setShelfLife(recipe.getShelfLife());

        // Convert ingredients array to List<Integer>
        List<Integer> ingredientsList = new ArrayList<>(recipe.getIngredients());
        recipeGrpc.addAllIngredients(ingredientsList);

        // Convert tags
        recipeGrpc.addAllTags(recipe.getTags().stream()
                .map(this::toTagGrpc)
                .collect(Collectors.toList()));

        return recipeGrpc.build();
    }

    @Mapping(target = "tags", expression = "java(toTagList(recipeGrpc.getTagsList()))")
    @Mapping(target = "ingredients", expression = "java(recipeGrpc.getIngredientsList())")
    Recipe toRecipe(RecipeGrpc recipeGrpc);

    @Mapping(target = "tags", expression = "java(toTagList(request.getTagsList()))")
    @Mapping(target = "ingredients", expression = "java(request.getIngredientsList())")
    Recipe fromCreateRequest(RecipeGrpcCreateRequest request);

    default TagGrpc toTagGrpc(Recipe.Tag tag) {
        if (tag == null) {
            return null;
        }

        TagGrpc.Builder tagGrpc = TagGrpc.newBuilder();

        if (tag.getId() != null) {
            tagGrpc.setId(tag.getId());
        }

        tagGrpc.addAllValues(tag.getValues());

        return tagGrpc.build();
    }

    default Recipe.Tag fromTagGrpc(TagGrpc tagGrpc) {
        if (tagGrpc == null) {
            return null;
        }

        return Recipe.Tag.builder()
                .id(tagGrpc.getId())
                .values(tagGrpc.getValuesList())
                .build();

    }

    default List<Recipe.Tag> toTagList(List<TagGrpc> tagGrpcs) {
        return tagGrpcs.stream()
                .map(this::fromTagGrpc)
                .collect(Collectors.toList());
    }
}