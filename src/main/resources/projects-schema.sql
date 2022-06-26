DROP TABLE IF EXISTS project_category;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS step;
DROP TABLE IF EXISTS material;
DROP TABLE IF EXISTS project;

CREATE TABLE project(
	project_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    project_name VARCHAR(64) NOT NULL,
    estimated_hours TIME NOT NULL,
    actual_HOURS TIME,
    difficulty INT NOT NULL,
    notes TEXT
);

CREATE TABLE material(
	material_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    project_id INT NOT NULL,
    material_name VARCHAR(64) NOT NULL,
    num_required INT NOT NULL,
    cost DECIMAL NOT NULL
);

CREATE TABLE step(
	step_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    project_id INT NOT NULL,
    step_text TEXT NOT NULL,
    step_order INT NOT NULL
);

CREATE TABLE category(
	category_id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    category_name VARCHAR(64) NOT NULL
);

CREATE TABLE project_category(
	project_id INT NOT NULL,
    category_id INT NOT NULL
);