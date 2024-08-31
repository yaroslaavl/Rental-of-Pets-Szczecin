--liquibase formatted sql

--changeset author:1
UPDATE webapp.users SET email_verification_token = 'default_token_value' WHERE email_verification_token IS NULL;


