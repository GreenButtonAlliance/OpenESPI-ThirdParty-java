INSERT INTO data_custodians (id, description, url) VALUES (1, 'ConEdison', 'http://localhost:8080/DataCustodian/login');
INSERT INTO data_custodians (id, description, url) VALUES (2, 'JCP&L', 'http://localhost:8080/DataCustodian/login');

INSERT INTO retail_customers (id, username, first_name, last_name, password, enabled, role) VALUES (1, 'alan',    'Alan',    'Turing',       'koala', 1, 'ROLE_USER,ROLE_CUSTOMER');
INSERT INTO retail_customers (id, username, first_name, last_name, password, enabled, role) VALUES (2, 'marissa', 'Marissa',  'Meyer',       'koala', 1, 'ROLE_USER,ROLE_CUSTOMER');
INSERT INTO retail_customers (id, username, first_name, last_name, password, enabled, role) VALUES (3, 'sam',     'Sam',      'White',       'koala', 1, 'ROLE_USER,ROLE_CUSTOMER');

