CREATE TABLE activities (
    id SERIAL PRIMARY KEY,
    description TEXT NOT NULL,
    due_date DATE,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    group_id INTEGER NOT NULL,
    CONSTRAINT fk_group
        FOREIGN KEY (group_id)
        REFERENCES activity_groups (id)
        ON DELETE CASCADE
);
