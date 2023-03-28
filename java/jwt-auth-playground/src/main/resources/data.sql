INSERT INTO users (name, passwd, created_at, created_by) VALUES
('admin', 'admin1', CURRENT_TIMESTAMP(), 'spring'),
('user', 'user1', CURRENT_TIMESTAMP(), 'spring')
;

INSERT INTO roles (role, created_at, created_by) VALUES
('ADMIN', CURRENT_TIMESTAMP(), 'spring'),
('READ', CURRENT_TIMESTAMP(), 'spring'),
('WRITE', CURRENT_TIMESTAMP(), 'spring');

INSERT INTO users_role (role_id, user_id, created_at, created_by) VALUES
(1, 1, CURRENT_TIMESTAMP(), 'spring'),
(2, 2, CURRENT_TIMESTAMP(), 'spring');


INSERT INTO hardware (name, category, created_at, created_by) VALUES
('Hp keyboard', 'KEYBOARD', CURRENT_TIMESTAMP(), 'spring'),
('MS keyboard', 'KEYBOARD', CURRENT_TIMESTAMP(), 'spring'),
('Think vision', 'SCREEN', CURRENT_TIMESTAMP(), 'spring')
;