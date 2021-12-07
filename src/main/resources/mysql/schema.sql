CREATE TABLE IF NOT EXISTS branches
(
    branch_id      BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    branch_name    VARCHAR(30),
    manager_id     BIGINT,
    mgr_start_date DATE
);

CREATE TABLE IF NOT EXISTS employees
(
    employee_id BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    first_name  VARCHAR(20),
    last_name   VARCHAR(20),
    birth_day   DATE,
    sex         CHARACTER,
    salary      DOUBLE UNSIGNED,
    super_id    BIGINT UNSIGNED,
    branch_id   BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (super_id) REFERENCES employees (employee_id),
    FOREIGN KEY (branch_id) REFERENCES branches (branch_id)
);

ALTER TABLE branches
    ADD CONSTRAINT fk_branches_employees FOREIGN KEY (manager_id) REFERENCES employees (employee_id);

CREATE TABLE IF NOT EXISTS clients
(
    client_id   BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    client_name VARCHAR(50),
    branch_id   BIGINT UNSIGNED NOT NULL,
    FOREIGN KEY (branch_id) REFERENCES branches (branch_id)
);


CREATE TABLE IF NOT EXISTS branch_suppliers
(
    branch_id     BIGINT UNSIGNED NOT NULL,
    supplier_name VARCHAR(50)     NOT NULL,
    supply_type   VARCHAR(50),
    PRIMARY KEY (branch_id, supplier_name),
    FOREIGN KEY (branch_id) REFERENCES branches (branch_id)
);


CREATE TABLE IF NOT EXISTS works_with
(
    employee_id BIGINT UNSIGNED NOT NULL,
    client_id   BIGINT UNSIGNED NOT NULL,
    total_sales DOUBLE UNSIGNED,
    PRIMARY KEY (employee_id, client_id),
    FOREIGN KEY (employee_id) REFERENCES employees (employee_id),
    FOREIGN KEY (client_id) REFERENCES clients (client_id)
);

