INSERT INTO permissions (name, api_path, method, module, created_at, created_by) VALUES
-- Roles Controller
('role created', '/roles', 'POST', 'ROLES', NOW(), 'system'),
('fetch role by ID', '/roles/{id}', 'GET', 'ROLES', NOW(), 'system'),
('fetch all roles', '/roles', 'GET', 'ROLES', NOW(), 'system'),
('update role', '/roles/{id}', 'PUT', 'ROLES', NOW(), 'system'),
('deleted a role', '/roles/{id}', 'DELETE', 'ROLES', NOW(), 'system'),

-- Companies Controller
('fetch all companies', '/companies', 'GET', 'COMPANIES', NOW(), 'system'),
('fetch company by ID', '/companies/{id}', 'GET', 'COMPANIES', NOW(), 'system'),
('company created', '/companies', 'POST', 'COMPANIES', NOW(), 'system'),
('update company', '/companies', 'PUT', 'COMPANIES', NOW(), 'system'),
('delete company', '/companies/{id}', 'DELETE', 'COMPANIES', NOW(), 'system'),

-- Auth Controller
('create a new account', '/auth/register', 'POST', 'AUTH', NOW(), 'system'),
('login successfully', '/auth/login', 'POST', 'AUTH', NOW(), 'system'),
('get refresh token', '/auth/refresh', 'GET', 'AUTH', NOW(), 'system'),
('fetch account', '/auth/account', 'GET', 'AUTH', NOW(), 'system'),
('logout successfully', '/auth/logout', 'POST', 'AUTH', NOW(), 'system'),

-- Users Controller
('fetch all users', '/users', 'GET', 'USERS', NOW(), 'system'),
('fetch user by id', '/users/{id}', 'GET', 'USERS', NOW(), 'system'),
('create a user', '/users', 'POST', 'USERS', NOW(), 'system'),
('update a user', '/users', 'PUT', 'USERS', NOW(), 'system'),
('delete a user', '/users/{id}', 'DELETE', 'USERS', NOW(), 'system'),

-- Permissions Controller
('permission created', '/permissions', 'POST', 'PERMISSIONS', NOW(), 'system'),
('fetch permission by ID', '/permissions/{id}', 'GET', 'PERMISSIONS', NOW(), 'system'),
('fetch all permissions', '/permissions', 'GET', 'PERMISSIONS', NOW(), 'system'),
('update permission', '/permissions/{id}', 'PUT', 'PERMISSIONS', NOW(), 'system'),
('delete permission', '/permissions/{id}', 'DELETE', 'PERMISSIONS', NOW(), 'system'),

-- Skills Controller
('fetch all skills', '/skills', 'GET', 'SKILLS', NOW(), 'system'),
('created a skill', '/skills', 'POST', 'SKILLS', NOW(), 'system'),

-- Jobs Controller
('job created', '/jobs', 'POST', 'JOBS', NOW(), 'system'),
('fetch job by ID', '/jobs/{id}', 'GET', 'JOBS', NOW(), 'system'),
('fetch all jobs', '/jobs', 'GET', 'JOBS', NOW(), 'system'),
('update job', '/jobs/{id}', 'PUT', 'JOBS', NOW(), 'system'),
('delete job', '/jobs/{id}', 'DELETE', 'JOBS', NOW(), 'system');

-- 1. Roles
INSERT INTO roles (role_id, name, description, active, created_at, created_by) VALUES
                                                                                   (1, 'SUPER_ADMIN', 'System administrator with full access', true, NOW(), 'system'),
                                                                                   (2, 'HR', 'Human Resources manager for a company', true, NOW(), 'system'),
                                                                                   (3, 'CANDIDATE', 'Standard user seeking jobs', true, NOW(), 'system');

-- 2. Permission_Role (Assuming permissions 1-27 exist from the previous script)
INSERT INTO permission_role (role_id, permission_id)
SELECT 1, permission_id FROM permissions;

INSERT INTO permission_role (role_id, permission_id)
SELECT 2, permission_id FROM permissions
WHERE (api_path, method) IN (
    -- Company: xem + cập nhật thông tin công ty mình
                             ('/companies', 'GET'),
                             ('/companies/{id}', 'GET'),
                             ('/companies', 'PUT'),

    -- Skill: chỉ cần xem để chọn khi tạo job
                             ('/skills', 'GET'),

    -- Job: toàn quyền quản lý job của công ty mình
                             ('/jobs', 'POST'),
                             ('/jobs/{id}', 'GET'),
                             ('/jobs', 'GET'),
                             ('/jobs/{id}', 'PUT'),
                             ('/jobs/{id}', 'DELETE'),

    -- Auth: hành động cơ bản của tài khoản (nếu bạn không dùng cách whitelist trong filter)
                             ('/auth/account', 'GET'),
                             ('/auth/logout', 'POST')
    );

INSERT INTO permission_role (role_id, permission_id)
SELECT 3, permission_id FROM permissions
WHERE (api_path, method) IN (
    -- Xem job để ứng tuyển
                             ('/jobs', 'GET'),
                             ('/jobs/{id}', 'GET'),

    -- Xem company để tìm hiểu nhà tuyển dụng
                             ('/companies', 'GET'),
                             ('/companies/{id}', 'GET'),

    -- Xem skill (có thể dùng để lọc job theo skill)
                             ('/skills', 'GET'),

    -- Auth: hành động cơ bản của tài khoản
                             ('/auth/account', 'GET'),
                             ('/auth/logout', 'POST')
    );
-- 3. Companies
INSERT INTO companies (company_id, name, description, address, logo, created_at, created_by) VALUES
                                                                                                 (1, 'TechNova Solutions', 'Leading software outsourcing company', '123 Tech Street, HCMC', 'technova.png', NOW(), 'system'),
                                                                                                 (2, 'FinServe Global', 'Fintech startup focusing on payment gateways', '456 Finance Blvd, Hanoi', 'finserve.png', NOW(), 'system');

-- 4. Users
-- Note: Passwords should normally be securely hashed (e.g., BCrypt). Using raw strings here for placeholder visibility.

-- 5. Skills
INSERT INTO skills (skill_id, name, created_at, created_by) VALUES
                                                                (1, 'Java', NOW(), 'system'),
                                                                (2, 'Spring Boot', NOW(), 'system'),
                                                                (3, 'ReactJS', NOW(), 'system'),
                                                                (4, 'MySQL', NOW(), 'system'),
                                                                (5, 'AWS', NOW(), 'system');

-- 6. Jobs
-- Using standard string values for JobLevel Enum (e.g., FRESHER, JUNIOR, SENIOR)
INSERT INTO jobs (job_id, name, location, salary, quantity, level, description, start_date, end_date, is_active, company_id, created_at, created_by) VALUES
                                                                                                                                                         (1, 'Backend Java Developer', 'HCMC', 1500, 3, 'JUNIOR', 'Develop and maintain backend APIs using Spring Boot.', NOW(), DATE_ADD(NOW(), INTERVAL 30 DAY), true, 1, NOW(), 'hr.alice@technova.com'),
                                                                                                                                                         (2, 'Frontend React Developer', 'Hanoi', 2000, 2, 'SENIOR', 'Lead the frontend development for our new payment portal.', NOW(), DATE_ADD(NOW(), INTERVAL 15 DAY), true, 2, NOW(), 'system');

-- 7. Job_Skill (Mapping Jobs to Skills)
INSERT INTO job_skill (id, job_id, skill_id) VALUES
                                                 (1, 1, 1), -- Backend Job needs Java
                                                 (2, 1, 2), -- Backend Job needs Spring Boot
                                                 (3, 1, 4), -- Backend Job needs MySQL
                                                 (4, 2, 3), -- Frontend Job needs ReactJS
                                                 (5, 2, 5); -- Frontend Job needs AWS
