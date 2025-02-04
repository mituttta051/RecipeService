-- Create the database
CREATE DATABASE recipe_service;

\c recipe_service;

-- Create the recipes table
CREATE TABLE IF NOT EXISTS recipe (
    id INT NOT NULL,
    space_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    ingredients INT[] NOT NULL,
    description TEXT NOT NULL,
    servings_number INT NOT NULL,
    cook_time INT NOT NULL,
    tags JSON,
    PRIMARY KEY (id, space_id)  
);

-- Create the ingredients table
CREATE TABLE IF NOT EXISTS ingredient (
    id INT NOT NULL,
    space_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id, space_id)
);

-- Create the tags table
CREATE TABLE IF NOT EXISTS tag (
    id INT NOT NULL,
    space_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    values TEXT[] NOT NULL,
    PRIMARY KEY (id, space_id)
); 