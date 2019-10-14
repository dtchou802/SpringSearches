-- CREATE TABLE user (
--   id INT AUTO_INCREMENT  PRIMARY KEY,
--   name VARCHAR(250) NOT NULL
-- );
--
-- CREATE TABLE department (
--   id INT AUTO_INCREMENT  PRIMARY KEY,
--   name VARCHAR(250) NOT NULL,
--   chairperson_id   integer references user(id)
--
-- );

INSERT INTO user (id,name) VALUES (1,'John');
INSERT INTO user (id,name) VALUES (2,'Jane');
INSERT INTO user (id,name) VALUES (3,'Tom');
INSERT INTO user (id,name) VALUES (4,'Kate');

INSERT INTO department (id,name,chairperson_id) VALUES (5,'Kate',1);

INSERT INTO search (id,department_id,position,committee_chair_id) VALUES (6,5,'Professor',2);
INSERT INTO search_committee_members (search_id,committee_members_id) values (6,2);
INSERT INTO search_committee_members (search_id,committee_members_id) values (6,3);

