DROP ALL OBJECTS;

CREATE TABLE branches
(
    branch_id      BIGINT IDENTITY PRIMARY KEY,
    branch_name    VARCHAR(30),
    manager_id     BIGINT,
    mgr_start_date DATE
);

CREATE TABLE employees
(
    employee_id BIGINT IDENTITY PRIMARY KEY,
    first_name  VARCHAR(20),
    last_name   VARCHAR(20),
    birth_day   DATE,
    sex         CHARACTER,
    salary      DOUBLE,
    super_id    BIGINT,
    branch_id   BIGINT NOT NULL
);
ALTER TABLE employees
    ADD CONSTRAINT fk_employees_employees FOREIGN KEY (super_id) REFERENCES employees (employee_id)
        ON DELETE SET NULL;
ALTER TABLE employees
    ADD CONSTRAINT fk_employees_branches FOREIGN KEY (branch_id) REFERENCES branches (branch_id)
        ON DELETE SET NULL;

ALTER TABLE branches
    ADD CONSTRAINT fk_branches_employees FOREIGN KEY (manager_id) REFERENCES employees (employee_id)
        ON DELETE SET NULL;

CREATE TABLE clients
(
    client_id   BIGINT IDENTITY PRIMARY KEY,
    client_name VARCHAR(50),
    branch_id   BIGINT NOT NULL
);
ALTER TABLE clients
    ADD CONSTRAINT fk_clients_branches FOREIGN KEY (branch_id) REFERENCES branches (branch_id)
        ON DELETE SET NULL;

CREATE TABLE branch_suppliers
(
    branch_id     BIGINT      NOT NULL,
    supplier_name VARCHAR(50) NOT NULL,
    supply_type   VARCHAR(50),
    PRIMARY KEY (branch_id, supplier_name)
);
ALTER TABLE branch_suppliers
    ADD CONSTRAINT fk_branch_supplier_branches FOREIGN KEY (branch_id) REFERENCES branches (branch_id)
        ON DELETE CASCADE;

CREATE TABLE works_with
(
    employee_id BIGINT NOT NULL,
    client_id   BIGINT NOT NULL,
    total_sales DOUBLE,
    PRIMARY KEY (employee_id, client_id)
);
ALTER TABLE works_with
    ADD CONSTRAINT fk_works_with_employees FOREIGN KEY (employee_id) REFERENCES employees (employee_id)
        ON DELETE SET NULL;
ALTER TABLE works_with
    ADD CONSTRAINT fk_works_with_clients FOREIGN KEY (client_id) REFERENCES clients (client_id)
        ON DELETE SET NULL;

