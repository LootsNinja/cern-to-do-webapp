CREATE TABLE task_categories
(
    category_id          NUMBER AUTO_INCREMENT PRIMARY KEY,
    category_name        VARCHAR(100) UNIQUE NOT NULL,
    category_description VARCHAR(500)
);
CREATE SEQUENCE TASK_CATEGORY_SEQ START WITH 1 INCREMENT BY 1;
CREATE UNIQUE INDEX category_name_uk ON task_categories (category_name);