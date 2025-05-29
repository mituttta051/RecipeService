-- Insert ingredients (space_id 1)
INSERT INTO ingredient (space_id, name) VALUES
(1, 'Chicken breast'),
(1, 'Rice'),
(1, 'Tomatoes'),
(1, 'Onions'),
(1, 'Garlic'),
(1, 'Olive oil'),
(1, 'Salt'),
(1, 'Pepper'),
(1, 'Pasta'),
(1, 'Cheese'),
(1, 'Mushrooms'),
(1, 'Bell peppers'),
(1, 'Beef'),
(1, 'Potatoes'),
(1, 'Carrots'),
(1, 'Broccoli'),
(1, 'Salmon'),
(1, 'Lemon'),
(1, 'Basil'),
(1, 'Oregano');

-- Insert tags (space_id 1)
INSERT INTO tag (space_id, name, values) VALUES
(1, 'Cuisine', '101=>Italian,102=>Asian,103=>Mediterranean,104=>American,105=>Mexican'),
(1, 'Difficulty', '201=>Easy,202=>Medium,203=>Hard'),
(1, 'MealType', '301=>MainDish,302=>SideDish,303=>Salad,304=>Soup'),
(1, 'Dietary', '401=>Vegetarian,402=>Vegan,403=>GlutenFree,404=>LowCarb'),
(1, 'CookingMethod', '501=>StirFry,502=>Baking,503=>Grilling,504=>Stewing'),
(1, 'PrepTime', '601=>Quick,602=>Medium,603=>Long'),
(1, 'Nutrition', '701=>HighProtein,702=>LowCalorie,703=>Balanced'),
(1, 'Season', '801=>AllYear,802=>Summer,803=>Winter,804=>Spring,805=>Fall'),
(1, 'Flavor', '901=>Spicy,902=>Sweet,903=>Savory,904=>Tangy'),
(1, 'Occasion', '1001=>Everyday,1002=>SpecialOccasion,1003=>PartyFood');

-- Insert recipes (space_id 1)
-- Recipe 1: Chicken Stir Fry
INSERT INTO recipe (space_id, name, ingredients, description, servings_number, cook_time, shelf_life, tags) VALUES
(1, 'Chicken Stir Fry', 
 ARRAY[1, 2, 4, 5, 6, 7, 8],
 'A quick and healthy Asian-inspired dish',
 4,
 30,
 2,
 '[{"id": 1, "values": [102]}, {"id": 2, "values": [201]}, {"id": 3, "values": [301]}, {"id": 6, "values": [601]}, {"id": 7, "values": [701]}, {"id": 8, "values": [801]}]'::jsonb);

-- Recipe 2: Spaghetti Carbonara
INSERT INTO recipe (space_id, name, ingredients, description, servings_number, cook_time, shelf_life, tags) VALUES
(1, 'Spaghetti Carbonara',
 ARRAY[9, 10, 5, 6, 7, 8],
 'Classic Italian pasta dish',
 4,
 25,
 1,
 '[{"id": 1, "values": [101]}, {"id": 2, "values": [202]}, {"id": 3, "values": [301]}, {"id": 6, "values": [601]}, {"id": 7, "values": [703]}, {"id": 8, "values": [801]}]'::jsonb);

-- Recipe 3: Vegetable Curry
INSERT INTO recipe (space_id, name, ingredients, description, servings_number, cook_time, shelf_life, tags) VALUES
(1, 'Vegetable Curry',
 ARRAY[2, 3, 4, 11, 12, 6, 7],
 'Spicy vegetarian curry',
 4,
 45,
 3,
 '[{"id": 1, "values": [102]}, {"id": 2, "values": [202]}, {"id": 3, "values": [301]}, {"id": 4, "values": [401]}, {"id": 9, "values": [901]}, {"id": 8, "values": [801]}]'::jsonb);

-- Recipe 4: Beef Stew
INSERT INTO recipe (space_id, name, ingredients, description, servings_number, cook_time, shelf_life, tags) VALUES
(1, 'Beef Stew',
 ARRAY[13, 14, 15, 4, 5, 6, 7, 8],
 'Hearty comfort food',
 6,
 120,
 4,
 '[{"id": 2, "values": [202]}, {"id": 3, "values": [301]}, {"id": 5, "values": [504]}, {"id": 6, "values": [603]}, {"id": 7, "values": [701]}, {"id": 8, "values": [803]}]'::jsonb);

-- Recipe 5: Grilled Salmon
INSERT INTO recipe (space_id, name, ingredients, description, servings_number, cook_time, shelf_life, tags) VALUES
(1, 'Grilled Salmon',
 ARRAY[17, 18, 6, 7, 8, 16],
 'Healthy fish dish',
 2,
 20,
 1,
 '[{"id": 2, "values": [201]}, {"id": 3, "values": [301]}, {"id": 5, "values": [503]}, {"id": 6, "values": [601]}, {"id": 7, "values": [701]}, {"id": 8, "values": [802]}]'::jsonb);

-- Recipe 6: Mushroom Risotto
INSERT INTO recipe (space_id, name, ingredients, description, servings_number, cook_time, shelf_life, tags) VALUES
(1, 'Mushroom Risotto',
 ARRAY[2, 11, 4, 5, 10, 6, 7],
 'Creamy Italian rice dish',
 4,
 40,
 2,
 '[{"id": 1, "values": [101]}, {"id": 2, "values": [202]}, {"id": 3, "values": [301]}, {"id": 6, "values": [602]}, {"id": 7, "values": [703]}, {"id": 8, "values": [801]}]'::jsonb);

-- Recipe 7: Stuffed Bell Peppers
INSERT INTO recipe (space_id, name, ingredients, description, servings_number, cook_time, shelf_life, tags) VALUES
(1, 'Stuffed Bell Peppers',
 ARRAY[12, 2, 3, 4, 10, 6, 7],
 'Vegetarian stuffed peppers',
 4,
 60,
 2,
 '[{"id": 2, "values": [202]}, {"id": 3, "values": [301]}, {"id": 4, "values": [401]}, {"id": 5, "values": [502]}, {"id": 6, "values": [602]}, {"id": 8, "values": [802]}]'::jsonb);

-- Recipe 8: Lemon Garlic Chicken
INSERT INTO recipe (space_id, name, ingredients, description, servings_number, cook_time, shelf_life, tags) VALUES
(1, 'Lemon Garlic Chicken',
 ARRAY[1, 18, 5, 6, 7, 8, 16],
 'Tangy chicken dish',
 4,
 45,
 3,
 '[{"id": 2, "values": [201]}, {"id": 3, "values": [301]}, {"id": 5, "values": [502]}, {"id": 6, "values": [602]}, {"id": 7, "values": [701]}, {"id": 9, "values": [904]}, {"id": 8, "values": [801]}]'::jsonb);

-- Recipe 9: Pasta Primavera
INSERT INTO recipe (space_id, name, ingredients, description, servings_number, cook_time, shelf_life, tags) VALUES
(1, 'Pasta Primavera',
 ARRAY[9, 3, 12, 11, 6, 19, 7],
 'Vegetable pasta dish',
 4,
 30,
 2,
 '[{"id": 1, "values": [101]}, {"id": 2, "values": [201]}, {"id": 3, "values": [301]}, {"id": 4, "values": [401]}, {"id": 6, "values": [601]}, {"id": 8, "values": [804]}]'::jsonb);

-- Recipe 10: Mediterranean Salad
INSERT INTO recipe (space_id, name, ingredients, description, servings_number, cook_time, shelf_life, tags) VALUES
(1, 'Mediterranean Salad',
 ARRAY[3, 4, 10, 6, 19, 20, 7],
 'Fresh and healthy salad',
 4,
 15,
 1,
 '[{"id": 1, "values": [103]}, {"id": 2, "values": [201]}, {"id": 3, "values": [303]}, {"id": 4, "values": [401]}, {"id": 6, "values": [601]}, {"id": 7, "values": [702]}, {"id": 8, "values": [802]}]'::jsonb); 