package cybercooker.recipeservice.mapper;

import cybercooker.recipeservice.entity.Recipe;
import cybercooker.recipeservice.grpc.recipe.RecipeCreateRequest;
import cybercooker.recipeservice.grpc.recipe.RecipeDTO;
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


    default RecipeDTO toRecipeDTO(Recipe recipe) {
        if (recipe == null) {
            return null;
        }

        RecipeDTO.Builder recipeDTO = RecipeDTO.newBuilder();

        recipeDTO.setId(recipe.getId());
        recipeDTO.setSpaceId(recipe.getSpaceId());
        recipeDTO.setName(recipe.getName());
        recipeDTO.setDescription(recipe.getDescription());
        recipeDTO.setServingsNumber(recipe.getServingsNumber());
        recipeDTO.setCookTime(recipe.getCookTime());

        // Convert ingredients array to List<Integer>
        List<Integer> ingredientsList = new ArrayList<>(recipe.getIngredients());
        recipeDTO.addAllIngredients(ingredientsList);

        // Convert tags
        recipeDTO.addAllTags(recipe.getTags().stream()
                .map(this::toTagDTO)
                .collect(Collectors.toList()));

        return recipeDTO.build();
    }

    @Mapping(target = "tags", expression = "java(toTagList(recipeDTO.getTagsList()))")
    @Mapping(target = "ingredients", expression = "java(recipeDTO.getIngredientsList())")
    Recipe fromDTOToRecipe(RecipeDTO recipeDTO);

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