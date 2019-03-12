DROP TABLE User_friend;
DROP TABLE User_;
DROP TABLE User_role;

CREATE TABLE User_role(
   id_user_role SERIAL PRIMARY KEY UNIQUE,
   role VARCHAR(30) NOT NULL
);

CREATE TABLE User_(
   id_user SERIAL PRIMARY KEY UNIQUE,
   login VARCHAR(30) UNIQUE NOT NULL,
   password VARCHAR(30) NOT NULL,
   email VARCHAR(30) NOT NULL, 
   first_name VARCHAR(30),
   last_name VARCHAR(30),
   photo bytea,
   id_user_role int REFERENCES User_role (id_user_role) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE User_friend(
   id_user_friend SERIAL PRIMARY KEY UNIQUE,
   user_FK int REFERENCES User_ (id_user) ON UPDATE CASCADE ON DELETE CASCADE,
   is_friend_FK int REFERENCES User_ (id_user) ON UPDATE CASCADE ON DELETE CASCADE
);

INSERT INTO User_role(role)
VALUES ('ADMIN');

INSERT INTO User_role(role)
VALUES ('USER');
