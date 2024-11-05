CREATE TABLE project (
    project_id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL
);

CREATE INDEX idx_project_id ON project(project_id);
CREATE INDEX idx_project_name ON project(name);
CREATE INDEX idx_project_id_name ON project(project_id, name);
