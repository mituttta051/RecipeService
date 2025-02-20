package cybercooker.recipeservice;

import cybercooker.recipeservice.entity.Tag;
import cybercooker.recipeservice.exception.AlreadyExistsException;
import cybercooker.recipeservice.exception.NotFoundException;
import cybercooker.recipeservice.repository.postgres.implementation.TagRepository;
import cybercooker.recipeservice.repository.postgres.utils.TagUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = RecipeServiceApplication.class)
public class TagRepositoryTests extends RepositoryTests {
    @Autowired
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DELETE FROM tag");
        jdbcTemplate.execute("DELETE FROM last_tag_id");
    }

    @Test
    void testSave() {
        Tag tag = Tag.builder()
                .id(1)
                .spaceId(1)
                .name("Difficulty")
                .values(TagUtils.map(List.of("Easy", "Medium", "Hard")))
                .build();
        tagRepository.save(tag);
        Tag savedTag = tagRepository.getById(1, 1);
        assertThat(savedTag).isEqualTo(tag);

        Tag tag2 = Tag.builder()
                .id(2)
                .spaceId(1)
                .name("Time")
                .values(TagUtils.map(List.of("Breakfast", "Lunch", "Dinner")))
                .build();
        tagRepository.save(tag2);
        Tag savedTag2 = tagRepository.getById(2, 1);
        assertThat(savedTag2).isEqualTo(tag2);

        Tag tag3 = Tag.builder()
                .id(1)
                .spaceId(2)
                .name("Type")
                .values(TagUtils.map(List.of("Egg", "Dessert", "Meat")))
                .build();
        tagRepository.save(tag3);
        Tag savedTag3 = tagRepository.getById(1, 2);
        assertThat(savedTag3).isEqualTo(tag3);

    }

    @Test
    void testSaveThatAlreadyExists() {
        Tag tag = Tag.builder()
                .id(1)
                .spaceId(1)
                .name("Type")
                .values(TagUtils.map(List.of("Egg", "Dessert", "Meat")))
                .build();
        tagRepository.save(tag);
        Tag savedTag = tagRepository.getById(1, 1);
        assertThat(savedTag).isEqualTo(tag);

        Tag tag2 = Tag.builder()
                .spaceId(1)
                .name("Type")
                .values(TagUtils.map(List.of("Egg")))
                .build();
        assertThrows(AlreadyExistsException.class, () -> tagRepository.save(tag2));
    }

    @Test
    void testGetByIdThatDoesNotExist() {
        assertThrows(NotFoundException.class, () -> tagRepository.getById(1, 1));
    }

    @Test
    void TestGetAllBySpaceId() {
        Tag tag = Tag.builder()
                .id(1)
                .spaceId(1)
                .name("Difficulty")
                .values(TagUtils.map(List.of("Easy", "Medium", "Hard")))
                .build();
        tagRepository.save(tag);

        Tag tag2 = Tag.builder()
                .id(2)
                .spaceId(1)
                .name("Time")
                .values(TagUtils.map(List.of("Breakfast", "Lunch", "Dinner")))
                .build();
        tagRepository.save(tag2);

        Tag tag3 = Tag.builder()
                .id(1)
                .spaceId(2)
                .name("Type")
                .values(TagUtils.map(List.of("Egg", "Dessert", "Meat")))
                .build();
        tagRepository.save(tag3);

        assertThat(tagRepository.getAllBySpaceId(1)).containsExactlyInAnyOrder(tag, tag2);
        assertThat(tagRepository.getAllBySpaceId(2)).containsExactlyInAnyOrder(tag3);
        assertThat(tagRepository.getAllBySpaceId(3)).isEmpty();
    }

    @Test
    void TestUpdate() {
        Tag tag = Tag.builder()
                .spaceId(1)
                .name("Difficulty")
                .values(TagUtils.map(List.of("Easy", "Medium", "Hard")))
                .build();
        tagRepository.save(tag);

        Tag updatedTag = Tag.builder()
                .id(1)
                .spaceId(1)
                .name("Time")
                .values(TagUtils.map(List.of("Breakfast", "Lunch", "Dinner")))
                .build();
        tagRepository.update(updatedTag);

        Tag savedTag = tagRepository.getById(1, 1);
        assertThat(savedTag).isEqualTo(updatedTag);
    }

    @Test
    void TestUpdateThatDoesNotExist() {
        Tag tag = Tag.builder()
                .id(1)
                .spaceId(1)
                .name("Difficulty")
                .values(TagUtils.map(List.of("Easy", "Medium", "Hard")))
                .build();
        assertThrows(NotFoundException.class, () -> tagRepository.update(tag));
    }

    @Test
    void TestUpdateThatAlreadyExists() {
        Tag tag = Tag.builder()
                .spaceId(1)
                .name("Difficulty")
                .values(TagUtils.map(List.of("Easy", "Medium", "Hard")))
                .build();
        tagRepository.save(tag);

        Tag tag2 = Tag.builder()
                .spaceId(1)
                .name("Time")
                .values(TagUtils.map(List.of("Breakfast", "Lunch", "Dinner")))
                .build();
        tagRepository.save(tag2);

        Tag updatedTag = Tag.builder()
                .id(1)
                .spaceId(1)
                .name("Time")
                .values(TagUtils.map(List.of("Breakfast")))
                .build();
        assertThrows(AlreadyExistsException.class, () -> tagRepository.update(updatedTag));
    }

    @Test
    void TestDelete() {
        Tag tag = Tag.builder()
                .spaceId(1)
                .name("Difficulty")
                .values(TagUtils.map(List.of("Easy", "Medium", "Hard")))
                .build();
        tagRepository.save(tag);

        tagRepository.delete(1, 1);
        assertThrows(NotFoundException.class, () -> tagRepository.getById(1, 1));
    }

    @Test
    void TestDeleteThatDoesNotExist() {
        assertThrows(NotFoundException.class, () -> tagRepository.delete(1, 1));
    }

}

