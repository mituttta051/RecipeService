package cybercooker.recipeservice.mapper;

import cybercooker.recipeservice.entity.Recipe;
import cybercooker.recipeservice.grpc.recipe.RecipeCreateRequest;
import cybercooker.recipeservice.grpc.recipe.RecipeRequestResponse;
import cybercooker.recipeservice.grpc.recipe.TagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface RecipeMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);


    default RecipeRequestResponse toRecipeDTO(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        RecipeRequestResponse.Builder recipeRequestResponse = RecipeRequestResponse.newBuilder();

        recipeRequestResponse.setId(recipe.getId());
        recipeRequestResponse.setSpaceId(recipe.getSpaceId());
        recipeRequestResponse.setName(recipe.getName());
        recipeRequestResponse.setDescription(recipe.getDescription());
        recipeRequestResponse.setServingsNumber(recipe.getServingsNumber());
        recipeRequestResponse.setCookTime(recipe.getCookTime());

        // Convert ingredients array to List<Integer>
        List<Integer> ingredientsList = new ArrayList<>(recipe.getIngredients());
        recipeRequestResponse.addAllIngredients(ingredientsList);

        // Convert tags
        recipeRequestResponse.addAllTags(recipe.getTags().stream()
                .map(this::toTagDTO)
                .collect(Collectors.toList()));

        return recipeRequestResponse.build();
    }

    @Mapping(target = "tags", expression = "java(toTagList(recipeDTO.getTagsList()))")
    @Mapping(target = "ingredients", expression = "java(recipeDTO.getIngredientsList())")
    Recipe fromDTOToRecipe(RecipeRequestResponse recipeDTO);

    @Mapping(target = "tags", expression = "java(toTagList(request.getTagsList()))")
    @Mapping(target = "ingredients", expression = "java(request.getIngredientsList())")
    Recipe fromRequestToRecipe(RecipeCreateRequest request);

    default TagDTO toTagDTO(Recipe.Tag tag) {
        if (tag == null) {
            return null;
        }

        TagDTO.Builder tagDTO = TagDTO.newBuilder();

        if (tag.getId() != null) {
            tagDTO.setId(tag.getId());
        }

        tagDTO.addAllValues(tag.getValues());

        return tagDTO.build();
    }

    default Recipe.Tag fromTagDTO(TagDTO tagDTO) {
        if (tagDTO == null) {
            return null;
        }

        return Recipe.Tag.builder()
                .id(tagDTO.getId())
                .values(tagDTO.getValuesList())
                .build();

    }

    default List<Recipe.Tag> toTagList(List<TagDTO> tagDTOs) {
        return tagDTOs.stream()
                .map(this::fromTagDTO)
                .collect(Collectors.toList());
    }
}