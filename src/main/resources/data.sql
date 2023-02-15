INSERT INTO roles (role_name)
SELECT 'ROLE_USER'
    WHERE
NOT EXISTS (
SELECT role_name FROM roles WHERE role_name = 'ROLE_USER'
);
INSERT INTO roles (role_name)
SELECT 'ROLE_ADMIN'
    WHERE
NOT EXISTS (
SELECT role_name FROM roles WHERE role_name = 'ROLE_ADMIN'
);