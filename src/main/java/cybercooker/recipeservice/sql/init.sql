-- Create the database
CREATE DATABASE recipe_service;

\c recipe_service;

-- Create the recipes table
CREATE TABLE recipe (
    id INT NOT NULL,
    spaceID INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    ingredients INT[] NOT NULL,
    description TEXT NOT NULL,
    servings_number INT NOT NULL,
    cook_time INT NOT NULL,
    tags JSON,
    PRIMARY KEY (id, spaceID)  
);

-- Create the ingredients table
CREATE TABLE ingredient (
    id INT NOT NULL,
    spaceID INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id, spaceID)
);

-- Create the tags table
CREATE TABLE tag (
    id INT NOT NULL,
    spaceID INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    values TEXT[] NOT NULL,
    PRIMARY KEY (id, spaceID)
); 