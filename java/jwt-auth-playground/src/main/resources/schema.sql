DROP TABLE IF EXISTS hardware;
DROP TABLE IF EXISTS users_role;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS roles;

CREATE TABLE hardware (
    hardware_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_at DATETIME,
    updated_by VARCHAR(255),
    PRIMARY KEY (hardware_id)
);

CREATE TABLE users (
    user_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    passwd VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_at DATETIME,
    updated_by VARCHAR(255),
    PRIMARY KEY (user_id)
);

CREATE TABLE roles (
    role_id INT NOT NULL AUTO_INCREMENT,
    role VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_at DATETIME,
    updated_by VARCHAR(255),
    PRIMARY KEY (role_id)
);

CREATE TABLE users_role (
    role_id INT NOT NULL,
    user_id INT NOT NULL,
    created_at DATETIME NOT NULL,
    created_by VARCHAR(255) NOT NULL,
    updated_at DATETIME,
    updated_by VARCHAR(255),
    PRIMARY KEY (role_id, user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);
