-- Create the database
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_database WHERE datname = 'recipe-db') THEN
        PERFORM dblink_exec('dbname=postgres', 'CREATE DATABASE "recipe-db"');
    END IF;
END
$$;

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
    PRIMARY KEY (id, space_id),
    UNIQUE (space_id, name)
);

-- Create the ingredients table
CREATE TABLE IF NOT EXISTS ingredient (
    id INT NOT NULL,
    space_id INT NOT NULL,
    name VARCHAR(32) NOT NULL,
    PRIMARY KEY (id, space_id),
    UNIQUE (space_id, name)
);

-- Create the tags table
CREATE TABLE IF NOT EXISTS tag (
    id INT NOT NULL,
    space_id INT NOT NULL,
    name VARCHAR(32) NOT NULL,
    values VARCHAR(64)[] NOT NULL,
    PRIMARY KEY (id, space_id),
    UNIQUE (space_id, name)
);

CREATE TABLE IF NOT EXISTS last_recipe_id (
    space_id INT NOT NULL PRIMARY KEY,
    last_id INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS last_ingredient_id (
    space_id INT NOT NULL PRIMARY KEY,
    last_id INT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS last_tag_id (
    space_id INT NOT NULL PRIMARY KEY,
    last_id INT NOT NULL DEFAULT 0
);


-- Create a function to increment and return the last_id
CREATE OR REPLACE FUNCTION get_next_id(space_id INT, table_name TEXT) RETURNS INT AS $$
DECLARE
    new_id INT;
BEGIN
    EXECUTE format('UPDATE %I SET last_id = last_id + 1 WHERE space_id = $1 RETURNING last_id', table_name) INTO new_id USING space_id;
    IF new_id IS NULL THEN
        EXECUTE format('INSERT INTO %I (space_id, last_id) VALUES ($1, 1) RETURNING last_id', table_name) INTO new_id USING space_id;
    END IF;
    RETURN new_id;
END;
$$ LANGUAGE plpgsql;

-- Create a trigger function to set the default id
CREATE OR REPLACE FUNCTION set_default_id() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.id IS NULL THEN
        NEW.id := get_next_id(NEW.space_id, TG_ARGV[0]);
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create triggers for each table
CREATE OR REPLACE TRIGGER set_recipe_id
    BEFORE INSERT ON recipe
    FOR EACH ROW
EXECUTE FUNCTION set_default_id('last_recipe_id');

CREATE OR REPLACE TRIGGER set_ingredient_id
    BEFORE INSERT ON ingredient
    FOR EACH ROW
EXECUTE FUNCTION set_default_id('last_ingredient_id');

CREATE OR REPLACE TRIGGER set_tag_id
    BEFORE INSERT ON tag
    FOR EACH ROW
EXECUTE FUNCTION set_default_id('last_tag_id');
