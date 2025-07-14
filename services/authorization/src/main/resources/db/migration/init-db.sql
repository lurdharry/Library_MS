-- Create the user_db database
CREATE DATABASE user_db;

-- Connect to user_db and create extension
\c user_db;
CREATE EXTENSION IF NOT EXISTS "pgcrypto";