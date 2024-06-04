-- liquibase formatted sql

-- changeset user:1

CREATE INDEX st_name_idx ON student (name);

-- changeset user:2

CREATE INDEX fc_color_name_idx on faculty (name, color);