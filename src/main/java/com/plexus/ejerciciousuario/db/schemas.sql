drop database if exists ejerciciousuario;
create database ejerciciousuario;
use ejerciciousuario;

CREATE TABLE users(
	user_id int PRIMARY KEY AUTO_INCREMENT,
	name_user varchar(50),
	email varchar(50)
) ENGINE=INNODB;

CREATE TABLE role(
  role_id int PRIMARY KEY AUTO_INCREMENT,
	name_role varchar(50)
) ENGINE=INNODB;

CREATE TABLE privilege(
	privilege_id int PRIMARY KEY AUTO_INCREMENT,
	name_privilege varchar(50)
) ENGINE=INNODB;

CREATE TABLE user_role(
	user_id int,
	role_id int,
	PRIMARY KEY(user_id, role_id),
	INDEX ind_idu(user_id),
  INDEX ind_idr(role_id),
  FOREIGN KEY(user_id) REFERENCES users(user_id) /*ON DELETE CASCADE ON UPDATE CASCADE*/,
  FOREIGN KEY(role_id) REFERENCES role(role_id) /*ON DELETE CASCADE ON UPDATE CASCADE*/
) ENGINE=INNODB;

CREATE TABLE role_privilege(
	role_id int,
	privilege_id int,
	PRIMARY KEY(role_id, privilege_id),
	INDEX ind_idr(role_id),
  INDEX ind_idp(privilege_id),
  FOREIGN KEY(role_id) REFERENCES role(role_id) /*ON DELETE CASCADE ON UPDATE CASCADE*/,
  FOREIGN KEY(privilege_id) REFERENCES privilege(privilege_id) /*ON DELETE CASCADE ON UPDATE CASCADE*/
) ENGINE=INNODB

-- add users
/*
insert into users(name_user,email) values ('suso','suso@gmail.com'),  ('mamel','mamel@gmailcom');
insert into role(name_role) values ('admin'),  ('user');
insert into privilege(name_privilege) values ('ver'),  ('escribir');

insert into role_privilege(role_id,privilege_id) values (1,1),  (2,2);
insert into user_role(role_id,user_id) values (1,1),  (2,2);*/