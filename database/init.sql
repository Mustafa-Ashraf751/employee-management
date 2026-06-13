-- =========================
-- Department
-- =========================
CREATE TABLE departments (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    location VARCHAR(255),
    budget NUMERIC(15,2) NOT NULL CHECK (budget >= 0)
);

-- =========================
-- Employee
-- =========================
CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(50),
    hire_date DATE NOT NULL,
    salary NUMERIC(12,2) NOT NULL CHECK (salary >= 0),

    department_id BIGINT NOT NULL,

    CONSTRAINT fk_employee_department
        FOREIGN KEY (department_id)
        REFERENCES departments(id)
        ON DELETE RESTRICT -- To avoid deleting a department with active employees
);

-- =========================
-- Project
-- =========================
CREATE TABLE projects (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    description TEXT,
    start_date DATE NOT NULL,
    end_date DATE,

    department_id BIGINT NOT NULL,

    CONSTRAINT fk_project_department
        FOREIGN KEY (department_id)
        REFERENCES departments(id)
        ON DELETE RESTRICT, -- To avoid deleting a department with active projects

    CONSTRAINT chk_project_dates
        CHECK (
            end_date IS NULL
            OR end_date >= start_date
        )
);

-- =========================
-- Employee Project
-- =========================
CREATE TABLE employee_projects (
    id BIGSERIAL PRIMARY KEY,

    employee_id BIGINT NOT NULL,
    project_id BIGINT NOT NULL,

    role VARCHAR(100) NOT NULL,

    CONSTRAINT fk_ep_employee
        FOREIGN KEY (employee_id)
        REFERENCES employees(id)
        ON DELETE CASCADE,  

    CONSTRAINT fk_ep_project
        FOREIGN KEY (project_id)
        REFERENCES projects(id)
        ON DELETE CASCADE,

    CONSTRAINT uk_employee_project
        UNIQUE(employee_id, project_id)
);