--liquibase formatted sql

--changeset author:1
ALTER TABLE webapp.users ALTER COLUMN email_verification_token SET NOT NULL;

