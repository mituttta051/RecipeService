-- Create the database
CREATE DATABASE recipe_service;

\c recipe_service;

-- Create the recipes table
CREATE TABLE IF NOT EXISTS recipe (
    id INT NOT NULL,
    space_id INT NOT NULL,
    name VARCHAR(32) NOT NULL,
    ingredients INT[] NOT NULL,
    description TEXT NOT NULL,
    servings_number INT NOT NULL,
    cook_time INT NOT NULL,
    tags JSON,
    PRIMARY KEY (id, space_id)  
);

CREATE TABLE IF NOT EXISTS last_recipe_id (
    space_id INT NOT NULL PRIMARY KEY,
    last_id INT NOT NULL
);

ALTER TABLE recipe ALTER COLUMN id SET DEFAULT get_next_id(space_id, 'last_recipe_id');

-- Create the ingredients table
CREATE TABLE IF NOT EXISTS ingredient (
    id INT NOT NULL,
    space_id INT NOT NULL,
    name VARCHAR(32) NOT NULL,
    PRIMARY KEY (id, space_id)
);

CREATE TABLE IF NOT EXISTS last_ingredient_id (
    space_id INT NOT NULL PRIMARY KEY,
    last_id INT NOT NULL
);

ALTER TABLE ingredient ALTER COLUMN id SET DEFAULT get_next_id(space_id, 'last_ingredient_id');

-- Create the tags table
CREATE TABLE IF NOT EXISTS tag (
    id INT NOT NULL,
    space_id INT NOT NULL,
    name VARCHAR(32) NOT NULL,
    values VARCHAR(64)[] NOT NULL,
    PRIMARY KEY (id, space_id)
);
CREATE TABLE IF NOT EXISTS last_tag_id (
    space_id INT NOT NULL PRIMARY KEY,
    last_id INT NOT NULL
);

ALTER TABLE tag ALTER COLUMN id SET DEFAULT get_next_id(space_id, 'last_tag_id');

-- Create a function to increment and return the last_id
CREATE OR REPLACE FUNCTION get_next_id(space_id INT, table_name TEXT) RETURNS INT AS $$
DECLARE
    new_id INT;
BEGIN
    EXECUTE format('UPDATE %I SET last_id = last_id + 1 WHERE space_id = $1 RETURNING last_id', table_name) INTO new_id USING space_id;
    RETURN new_id;
END;
$$ LANGUAGE plpgsql;

