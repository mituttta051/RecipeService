#!/bin/bash

# Connect to PostgreSQL and execute the SQL file
PGPASSWORD=secret psql -h localhost -p 5432 -U admin -d recipeservice -f src/main/resources/db/insert_data.sql

echo "Data insertion completed!" 