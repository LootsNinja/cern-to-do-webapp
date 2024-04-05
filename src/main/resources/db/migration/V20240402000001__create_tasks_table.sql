CREATE TABLE tasks
(
    task_id          NUMBER AUTO_INCREMENT PRIMARY KEY,
    task_name        VARCHAR(100) NOT NULL,
    task_description VARCHAR(500),
    deadline         TIMESTAMP,
    category_id      NUMBER,
    CONSTRAINT task_categories_fk FOREIGN KEY (category_id) REFERENCES task_categories (category_id)
);
CREATE SEQUENCE TASK_SEQ START WITH 1 INCREMENT BY 1;
CREATE UNIQUE INDEX tasks_pk ON tasks (task_id);