-- Création de la table
CREATE TABLE task (
    task_id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    user_id INT NOT NULL,
    project_id INT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    FOREIGN KEY (project_id) REFERENCES project(project_id) ON DELETE CASCADE,
);

-- Création des index
CREATE INDEX idx_task_id ON task(task_id);
CREATE INDEX idx_task_title ON task(title);
CREATE INDEX idx_project_id_name ON project(task_id, title);

-- Création de la vue matérialisée
CREATE MATERIALIZED VIEW tasks_per_project AS
SELECT p.projet_id, p.name,
COUNT(t.task_id) AS task_nb
FROM projet p
JOIN task t ON p.projet_id = t.projet_id GROUP BY p.projet_id, p.name;