-- =========================
-- Departments
-- =========================
INSERT INTO departments (name, location, budget)
VALUES
('Engineering', 'Cairo', 1000000),
('Human Resources', 'Giza', 250000),
('Finance', 'Alexandria', 500000);

-- =========================
-- Employees
-- =========================
INSERT INTO employees (
    name,
    email,
    phone,
    hire_date,
    salary,
    department_id
)
VALUES
('Ahmed Hassan', 'ahmed.hassan@example.com', '01012345678', '2023-01-15', 15000, 1),
('Sara Mohamed', 'sara.mohamed@example.com', '01023456789', '2022-06-10', 18000, 1),
('Omar Ali', 'omar.ali@example.com', '01034567890', '2024-02-01', 12000, 2),
('Mona Ibrahim', 'mona.ibrahim@example.com', '01045678901', '2021-11-20', 22000, 3),
('Youssef Mahmoud', 'youssef.mahmoud@example.com', '01056789012', '2023-09-05', 14000, 1);

-- =========================
-- Projects
-- =========================
INSERT INTO projects (
    name,
    description,
    start_date,
    end_date,
    department_id
)
VALUES
(
    'Employee Management System',
    'Internal HR and employee tracking platform',
    '2025-01-01',
    '2025-12-31',
    1
),
(
    'Payroll Automation',
    'Automating payroll calculations and reports',
    '2025-03-01',
    '2025-09-30',
    3
),
(
    'Recruitment Portal',
    'Online recruitment and candidate management',
    '2025-02-15',
    NULL,
    2
);

-- =========================
-- Employee Projects
-- =========================
INSERT INTO employee_projects (
    employee_id,
    project_id,
    role
)
VALUES
(1, 1, 'Backend Developer'),
(2, 1, 'Project Manager'),
(5, 1, 'Frontend Developer'),

(4, 2, 'Business Analyst'),
(2, 2, 'Technical Lead'),

(3, 3, 'HR Specialist'),
(1, 3, 'Technical Consultant');