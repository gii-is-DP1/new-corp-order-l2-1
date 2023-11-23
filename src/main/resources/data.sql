--Insert two authorities: admin and user
INSERT INTO authorities(id,name) VALUES (1,'ADMIN');
INSERT INTO authorities(id,name) VALUES (2,'USER');


-- One admin user, named admin1 with password 4dm1n and authority admin
-- One user named user1 with password 0wn3r and authority user
-- one user named user with password user and authority user

INSERT INTO users(id,username,password,authority) VALUES (1,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1);
INSERT INTO users(id,username,password,authority) VALUES (99,'user','$2a$10$dq4k1SV8hd8mzkD2aC/e3uu4rPFHHLN8Lw7WGxVqViTFmUlDbxQX2',2);
INSERT INTO users(id,username,password,authority) VALUES (4,'user1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);


